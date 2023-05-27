package ae.encodelab.basics.service.model.stats;

import lombok.Builder;

@Builder
public class SingleCourseStatistics {
    private final ImplementableResult<Double> average;
    private final ImplementableResult<Boolean> passed;

    public Double getAverage() {
        return average.get();
    }

    public Double getAverageOrElse(double defaultValue) {
        return average.getOrElse(defaultValue);
    }

    public Boolean isPassed() {
        return passed.get();
    }

    public Boolean isPassedOrElse(boolean defaultValue) {
        return passed.getOrElse(defaultValue);
    }
}
