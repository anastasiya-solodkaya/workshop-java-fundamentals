package ae.encodelab.basics.service.model.journals;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class StudentCourseJournalRecord {
    private String code;
    private String name;
    private List<Integer> marks;
}
