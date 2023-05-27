package ae.encodelab.basics.data.csv.management;

import ae.encodelab.basics.data.csv.model.Person;
import ae.encodelab.basics.data.csv.model.student.Student;
import ae.encodelab.basics.data.csv.model.student.StudentRegistration;
import ae.encodelab.basics.data.csv.model.student.journal.CourseMark;
import ae.encodelab.basics.data.csv.model.student.journal.StudentJournal;
import ae.encodelab.basics.data.csv.model.student.journal.StudentJournalRecord;
import ae.encodelab.basics.data.csv.model.teacher.Teacher;
import ae.encodelab.basics.data.csv.model.teacher.TeacherPosition;
import ae.encodelab.basics.data.csv.model.uni.Course;
import ae.encodelab.basics.data.csv.model.uni.Department;
import ae.encodelab.basics.data.csv.storage.CsvStudentsStorage;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentStorageManager {

    public static final String DEPARTMENTS_FILENAME = "departments.csv";
    public static final String COURSES_FILENAME = "courses.csv";
    public static final String STUDENTS_FILENAME = "students.csv";
    public static final String TEACHER_JOBS = "teacher_jobs.csv";
    public static final String TEACHER = "teachers.csv";
    public static final String JOURNAL = "journal.csv";
    private final Path path;

    private Map<String, Department> departmentsByCode;
    private Map<String, Course> coursesByCode;

    public StudentStorageManager(Path path) {
        this.path = path;
    }

    public CsvStudentsStorage load() throws Exception {
        if (!path.toFile().isDirectory()) {
            throw new Exception("Error: unable to find data at path " + path);
        }
        List<Department> departments = loadFromFile(DEPARTMENTS_FILENAME,
                DepartmentCSVRecord.class,
                StudentStorageManager::createDepartment);
        departmentsByCode = departments.stream()
                .collect(Collectors.toMap(Department::code, Function.identity()));
        List<Course> courses = loadFromFile(COURSES_FILENAME,
                CourseCSVRecord.class,
                StudentStorageManager::createCourse);
        coursesByCode = courses.stream()
                .collect(Collectors.toMap(Course::code, Function.identity()));

        List<Student> students = loadFromFile(STUDENTS_FILENAME, StudentCSVRecord.class,
                this::createStudent);
//        List<TeacherJobCSVRecord> teacherJobs = loadFromFile(TEACHER_JOBS, TeacherJobCSVRecord.class);

//        Map<Long, List<TeacherPosition>> teacherPositionsMap =
//                teacherJobs.stream().collect(
//                        Collectors.groupingBy(TeacherJobCSVRecord::getTeacher,
//                                Collectors.mapping(this::createTeacherPosition, Collectors.toList())));

//        List<Teacher> teachers = loadFromFile(TEACHER, PersonCSVRecord.class,
//                s -> new Teacher(createPerson(s), teacherPositionsMap.get(s.getId())));

        List<StudentJournalCSVRecord> journalsCSV = loadFromFile(JOURNAL, StudentJournalCSVRecord.class);


        Map<Long, StudentJournal> journals = new HashMap<>();
        for (StudentJournalCSVRecord journal : journalsCSV) {
            String courseCode = journal.getCourseCode();
            long studentId = journal.getStudentId();

            StudentJournal studentJournal = journals.getOrDefault(studentId, new StudentJournal(studentId, new ArrayList<>()));
            journals.put(studentId, studentJournal);

            List<Integer> marks = Arrays.stream(journal.getMarks().split("\\s+")).map(Integer::parseInt).toList();
            StudentJournalRecord record = studentJournal.records().stream().filter(r -> r.year() == journal.getYear()).findFirst().orElse(null);
            if(record == null) {
                record = new StudentJournalRecord(journal.getYear(), journal.getYearOfEducation(), new ArrayList<>());
                studentJournal.records().add(record);
            }
            record.marks().add(new CourseMark(coursesByCode.get(courseCode), marks));
        }

        return CsvStudentsStorage.builder()
                .students(students)
                .departments(departments)
                .courses(courses)
                .journal(journals.values().stream().toList())
                .build();
    }


    private <T> List<T> loadFromFile(String fileName, Class<T> clazz) throws IOException {
        return loadFromFile(fileName, clazz, Function.identity());
    }

    private <T, P> List<P> loadFromFile(String fileName, Class<T> clazz, Function<T, P> convert) throws IOException {
        Path filePath = path.resolve(fileName);
        InputStream inputStream = Files.newInputStream(filePath);
        Reader reader = new BufferedReader(new InputStreamReader(inputStream));
        try (reader) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<T> csvs = csvToBean.parse();
            return csvs.stream().map(convert).toList();
        }
    }

    private TeacherPosition createTeacherPosition(TeacherJobCSVRecord s) {
        return new TeacherPosition(s.getFromYear(), departmentsByCode.get(s.getDepartmentCode()), s.getTitle());
    }

    private Student createStudent(StudentCSVRecord s) {
        return new Student(s.getId(), createPerson(s), createStudentRegistration(s, departmentsByCode.get(s.getDepartmentCode())));
    }

    private static StudentRegistration createStudentRegistration(StudentCSVRecord s, Department department) {
        return new StudentRegistration(s.getYearOfRegistration(), s.getYearOfEducation(), department);
    }

    private static Person createPerson(PersonCSVRecord s) {
        return new Person(s.getFirstName(), s.getLastName(), s.isGender(), s.getBirthDate());
    }

    private static Department createDepartment(DepartmentCSVRecord d) {
        return new Department(d.getCode(), d.getName());
    }

    private static Course createCourse(CourseCSVRecord d) {
        return new Course(d.getCode(), d.getName());
    }
}
