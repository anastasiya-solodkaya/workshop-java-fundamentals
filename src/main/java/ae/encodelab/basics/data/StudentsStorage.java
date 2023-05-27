package ae.encodelab.basics.data;

import ae.encodelab.basics.data.csv.model.student.Student;
import ae.encodelab.basics.data.csv.model.student.journal.StudentJournal;
import ae.encodelab.basics.data.csv.model.uni.Department;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentsStorage {
    Page<Student> getStudents(int page, int defaultPageSize);
    Page<Student> getStudents(String departmentCode, int page, int defaultPageSize);

    Student getStudent(long id);

    StudentJournal getStudentJournal(long id);

    List<Department> getDepartments();
}
