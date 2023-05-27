package ae.encodelab.basics.data.csv.storage;

import ae.encodelab.basics.data.StudentsStorage;
import ae.encodelab.basics.data.csv.model.student.Student;
import ae.encodelab.basics.data.csv.model.student.journal.StudentJournal;
import ae.encodelab.basics.data.csv.model.uni.Course;
import ae.encodelab.basics.data.csv.model.uni.Department;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Builder
public class CsvStudentsStorage implements StudentsStorage {

    public static final Comparator<Student> STUDENTS_DEFAULT_ORDER =
            Comparator.comparing((Student s) -> s.person().lastName())
                    .thenComparing(s -> s.person().firstName());
    private List<Course> courses;
    private List<Department> departments;
//    private List<Teacher> teachers;
    private List<Student> students;
    private List<StudentJournal> journal;

    @Override
    public Page<Student> getStudents(int page, int defaultPageSize) {
        List<Student> orderedStudents = students.stream().sorted(STUDENTS_DEFAULT_ORDER).toList();
        return extractPage(orderedStudents, page, defaultPageSize);
    }

    private static <T> PageImpl<T> extractPage(List<T> list, int page, int defaultPageSize) {
        int total = list.size();
        int totalPages = total / defaultPageSize + (total % defaultPageSize == 0 ? 0 : total % defaultPageSize);

        int actualPage = page < 0 ? 0 : Math.min(page, totalPages);

        int from = actualPage * defaultPageSize;
        int to = Math.min(from + defaultPageSize, total);

        return new PageImpl<>(list.subList(from, to),
                Pageable.ofSize(defaultPageSize)
                        .withPage(actualPage), total);
    }

    @Override
    public Page<Student> getStudents(String departmentCode, int page, int defaultPageSize) {
        List<Student> orderedStudents = students.stream()
                .filter(s -> s.university().department().code().equalsIgnoreCase(departmentCode))
                .sorted(STUDENTS_DEFAULT_ORDER)
                .toList();
        return extractPage(orderedStudents, page, defaultPageSize);
    }

    @Override
    public Student getStudent(long id) {
        return students.stream().filter(t -> t.id() == id).findFirst().orElse(null);
    }

    @Override
    public StudentJournal getStudentJournal(long id) {
        return journal.stream().filter(j -> j.studentId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Department> getDepartments() {
        return new ArrayList<>(departments);
    }
}
