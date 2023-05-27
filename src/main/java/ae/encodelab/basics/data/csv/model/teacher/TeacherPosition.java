package ae.encodelab.basics.data.csv.model.teacher;

import ae.encodelab.basics.data.csv.model.uni.Department;

public record TeacherPosition(int yearOfJobStart, Department department, String currentTitle) {
}
