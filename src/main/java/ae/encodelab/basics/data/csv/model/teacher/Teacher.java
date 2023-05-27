package ae.encodelab.basics.data.csv.model.teacher;

import ae.encodelab.basics.data.csv.model.Person;

import java.util.List;

public record Teacher(Person person, List<TeacherPosition> positions) {
}
