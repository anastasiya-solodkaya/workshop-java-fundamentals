package ae.encodelab.basics.service.model.journals;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CourseMarks {
    private final String name;
    private final String code;
    private final List<Integer> marks;
}
