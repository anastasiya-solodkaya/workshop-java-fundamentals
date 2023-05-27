package ae.encodelab.basics.data.csv.model.student;

import ae.encodelab.basics.data.csv.model.uni.Department;

public record StudentRegistration(int yearOfAdmission, int yearOfEducation, Department department) {
}
