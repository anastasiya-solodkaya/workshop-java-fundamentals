package ae.encodelab.basics.service.model.journals;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class CourseMarks {
    private final String name;
    private final String code;
    private final List<Integer> marks;
}
