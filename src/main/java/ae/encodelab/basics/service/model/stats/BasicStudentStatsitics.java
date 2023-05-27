package ae.encodelab.basics.service.model.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class BasicStudentStatsitics {
    private final Integer countOfYearRepeats;
    private final Boolean graduatingThisYear;
    private final Boolean fresher;
    private final Integer currentAge;
    private final Boolean birthdayToday;
}
