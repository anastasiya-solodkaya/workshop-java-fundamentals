package ae.encodelab.basics.data.csv.model;

import java.time.LocalDate;

public record Person(String firstName, String lastName, boolean gender, LocalDate birthDate) {
}
