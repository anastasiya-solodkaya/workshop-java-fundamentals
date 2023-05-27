package ae.encodelab.basics.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class PersonalInfo {
    private final String firstName;
    private final String lastName;
    private final boolean gender;
    private final LocalDate dateOfBirth;

}
