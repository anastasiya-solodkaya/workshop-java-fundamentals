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
    private final Risk risk;
    private final Map<String, SingleCourseStatistics> courseAverageScore;

    public boolean isNoRisk(){
        return risk == Risk.NO_RISK;
    }
    public boolean isHighRisk(){
        return risk == Risk.HIGH;
    }
    public boolean isLowRisk(){
        return risk == Risk.LOW;
    }
    public boolean isMediumRisk(){
        return risk == Risk.MEDIUM;
    }
}
