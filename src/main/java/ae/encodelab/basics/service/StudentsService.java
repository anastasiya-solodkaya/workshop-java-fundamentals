package ae.encodelab.basics.service;

import ae.encodelab.basics.data.csv.model.student.Student;
import ae.encodelab.basics.data.csv.model.uni.Department;
import ae.encodelab.basics.service.model.StudentBasicInfo;
import ae.encodelab.basics.service.model.StudentDetailedInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentsService {
    Page<StudentBasicInfo> loadStudents(int page, int defaultPageSize);

    Page<StudentBasicInfo> loadStudents(String departmentCode, int page, int defaultPageSize);

    StudentDetailedInfo loadStudent(long id);

    List<Department> loadAllDepartments();

    String getDepartmentName(String code);
}
