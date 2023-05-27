package ae.encodelab.basics.service.model.journals;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class StudentJournalYearRecord {
    private int year;
    private int yearOfEducation;
    private List<CourseMarks> marks;
}
