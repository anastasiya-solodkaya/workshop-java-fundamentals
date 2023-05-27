package ae.encodelab.basics.data.csv.model.student;

import ae.encodelab.basics.data.csv.model.Person;

public record Student(long id, Person person, StudentRegistration university) {
}
