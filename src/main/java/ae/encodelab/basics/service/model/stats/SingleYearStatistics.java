package ae.encodelab.basics.service.model.stats;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class SingleYearStatistics {
    private final double averageScore;
    private final double maxCourseScore;
    private final double minCourseScore;
    private final boolean passed;
    private final int year;
    private final String scholarshipSupport;
    private final Map<String, SingleCourseStatistics> courseAverageScore;

}
