package ae.encodelab.basics.service.model.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class BasicStudentStatsitics {
    private final ImplementableResult<Integer> countOfYearRepeats;
    private final ImplementableResult<Boolean> graduatingThisYear;
    private final ImplementableResult<Boolean> fresher;
    private final ImplementableResult<Integer> currentAge;
    private final ImplementableResult<Boolean> birthdayToday;

    public Integer getCountOfYearRepeats() {
        return countOfYearRepeats.get();
    }

    public Boolean isGraduatingThisYear() {
        return graduatingThisYear.get();
    }

    public Boolean isFresher() {
        return fresher.get();
    }

    public Integer getCurrentAge() {
        return currentAge.get();
    }

    public Boolean isBirthdayToday() {
        return birthdayToday.get();
    }
}
