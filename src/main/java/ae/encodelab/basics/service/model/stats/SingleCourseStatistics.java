package ae.encodelab.basics.service.model.stats;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SingleCourseStatistics {
    private final double average;
    private final boolean passed;
}
