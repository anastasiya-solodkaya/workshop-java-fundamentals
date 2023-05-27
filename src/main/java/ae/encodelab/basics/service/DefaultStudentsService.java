package ae.encodelab.basics.service;

import ae.encodelab.basics.data.StudentsStorage;
import ae.encodelab.basics.data.csv.model.student.Student;
import ae.encodelab.basics.data.csv.model.student.journal.StudentJournal;
import ae.encodelab.basics.data.csv.model.student.journal.StudentJournalRecord;
import ae.encodelab.basics.data.csv.model.uni.Department;
import ae.encodelab.basics.service.model.PersonalInfo;
import ae.encodelab.basics.service.model.StudentBasicInfo;
import ae.encodelab.basics.service.model.StudentDetailedInfo;
import ae.encodelab.basics.service.model.journals.CourseMarks;
import ae.encodelab.basics.service.model.journals.StudentJournalYearRecord;
import ae.encodelab.basics.service.model.stats.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultStudentsService implements StudentsService {


    private final StudentsStorage storage;

    private final StudentsDataProcessingExtension extension = new StudentsDataProcessingExtension();

    public DefaultStudentsService(StudentsStorage storage) {
        this.storage = storage;
    }

    @Override
    public Page<StudentBasicInfo> loadStudents(int page, int defaultPageSize) {
        Page<Student> students = storage.getStudents(page, defaultPageSize);
        return students.map(this::processStudent);
    }

    @Override
    public Page<StudentBasicInfo> loadStudents(String departmentCode, int page, int defaultPageSize) {
        Page<Student> students = storage.getStudents(departmentCode, page, defaultPageSize);
        return students.map(this::processStudent);
    }

    private StudentBasicInfo processStudent(Student student) {

        return StudentBasicInfo.builder()
                .id(student.id())
                .personalInfo(makePersonalInfo(student))
                .currentYearOfEducation(student.university().yearOfEducation())
                .yearStarted(student.university().yearOfAdmission())
                .stats(makeBasicStat(student))
                .departmentName(student.university().department().name())
                .departmentCode(student.university().department().code())
                .build();
    }

    private static PersonalInfo makePersonalInfo(Student student) {
        return PersonalInfo.builder()
                .firstName(student.person().firstName())
                .lastName(student.person().lastName())
                .gender(student.person().gender())
                .dateOfBirth(student.person().birthDate())
                .build();
    }

    private BasicStudentStatsitics makeBasicStat(Student student) {
        return BasicStudentStatsitics.builder()
                .birthdayToday(ImplementableResult.of(() -> extension.isBirthdayToday(student.person().birthDate())))
                .currentAge(ImplementableResult.of(() -> extension.getCurrentAge(student.person().birthDate())))
                .fresher(ImplementableResult.of(() -> extension.isFresher(student.university().yearOfAdmission())))
                .graduatingThisYear(ImplementableResult.of(() -> extension.isGraduatingThisYear(student.university().yearOfEducation())))
                .countOfYearRepeats(ImplementableResult.of(() -> extension.getCountOfRepeatingYears(student.university().yearOfAdmission(),
                        student.university().yearOfEducation())))
                .build();
    }

    private List<SingleYearStatistics> makeMarksStat(Student student, List<StudentJournalYearRecord> journals) {
        List<SingleYearStatistics> stats = new ArrayList<>();
        SingleYearStatistics previous = null;
        for (StudentJournalYearRecord mark : journals) {
            List<CourseMarks> courseMarks = mark.getMarks();
            Map<String, SingleCourseStatistics> averages = new HashMap<>();
            for (CourseMarks marks : courseMarks) {
                String courseCode = marks.getCode();
                ImplementableResult<Double> avgScore = ImplementableResult.of(()->extension.calculateAverageScore(
                        marks.getMarks().stream().mapToInt(t -> t).toArray()));
                ImplementableResult<Boolean> passed = ImplementableResult.of( () -> extension.checkCoursePassed(avgScore.getOrElse(0.0)));
                averages.put(courseCode, SingleCourseStatistics.builder()
                        .average(avgScore)
                        .passed(passed)
                        .build());
            }
            double[] coursesAverages = averages.values().stream().mapToDouble(s -> s.getAverageOrElse(0.0)).toArray();
            boolean[] passes = new boolean[averages.size()];
            List<SingleCourseStatistics> values = new ArrayList<>(averages.values());
            for (int i = 0; i < values.size(); i++) {
                passes[i] = values.get(i).isPassedOrElse(false);
            }
            int[] allMarks = courseMarks.stream().flatMapToInt(t -> t.getMarks().stream().mapToInt(o -> o)).toArray();
            ImplementableResult<Double> averageYearScore = ImplementableResult.of(() -> extension.calculateAverageScore(allMarks));
            ImplementableResult<ScholarshipSupport> support;
            if (previous == null) {
                support = ImplementableResult.of(() -> ScholarshipSupport.STANDARD);
            } else {
                SingleYearStatistics p = previous;
                ImplementableResult<ScholarshipSupport> calculatedSupport = ImplementableResult.of(() -> extension.getScholarshipSupport(
                        p.isPassedOrElse(false),
                        p.getAverageScoreOrElse(0.0),
                        p.getMinCourseScoreOrElse(0.0))
                );
                support = calculatedSupport;
            }
            SingleYearStatistics yearStatistics = SingleYearStatistics.builder()
                    .courseAverageScore(averages)
                    .year(mark.getYear())
                    .maxCourseScore(ImplementableResult.of(() -> extension.findMaxCourseScore(coursesAverages)))
                    .minCourseScore(ImplementableResult.of(() -> extension.findMinCourseScore(coursesAverages)))
                    .averageScore(averageYearScore)
                    .passed(ImplementableResult.of(() -> extension.checkYearPassed(passes)))
                    .scholarshipSupport(support)
                    .build();
            previous = yearStatistics;
            stats.add(yearStatistics);
        }


        return stats;
    }

    @Override
    public StudentDetailedInfo loadStudent(long id) {
        Student student = storage.getStudent(id);

        if(student == null){
//            raise exception
        }

        StudentJournal journal = storage.getStudentJournal(id);
        List<StudentJournalYearRecord> journals = Collections.emptyList();
        if(journal != null) {
            System.out.println(journal.records());
            journals = journal.records().stream()
                    .sorted(Comparator.comparingInt(StudentJournalRecord::year))
                    .map(j -> StudentJournalYearRecord.builder()
                            .yearOfEducation(j.yearOfEducation())
                            .year(j.year())
                            .marks(j.marks().stream()
                                    .collect(Collectors.toMap(c -> c.course().code(),
                                            c -> CourseMarks.builder()
                                                    .code(c.course().code())
                                                    .name(c.course().name())
                                                    .marks(c.marks())
                                                    .build()))
                                    .values()
                                    .stream()
                                    .sorted(Comparator.comparing(CourseMarks::getName))
                                    .toList())
                            .build()
                    )
                    .toList();
        }


        return StudentDetailedInfo.builder()
                .id(student.id())
                .personalInfo(makePersonalInfo(student))
                .departmentCode(student.university().department().code())
                .departmentName(student.university().department().name())
                .currentYearOfEducation(student.university().yearOfEducation())
                .yearStarted(student.university().yearOfAdmission())
                .stats(makeBasicStat(student))
                .journal(journals)
                .marksStatistics(makeMarksStat(student, journals))
                .build();
    }

    @Override
    public List<Department> loadAllDepartments() {
        return new ArrayList<>(storage.getDepartments());
    }

    @Override
    public String getDepartmentName(String code) {
        Department department = storage.getDepartments().stream().filter(t -> t.code().equalsIgnoreCase(code)).findFirst().orElse(null);
        return department == null ? "<Not Existing>" : department.name();
    }
}
