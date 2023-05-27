package ae.encodelab.basics.service.model.stats;

import lombok.Builder;

import java.util.Map;

@Builder
public class SingleYearStatistics {
    private final ImplementableResult<Double> averageScore;
    private final ImplementableResult<Double> maxCourseScore;
    private final ImplementableResult<Double> minCourseScore;
    private final ImplementableResult<Boolean> passed;
    private final int year;
    private final ImplementableResult<ScholarshipSupport> scholarshipSupport;
    private final Map<String, SingleCourseStatistics> courseAverageScore;

    public Double getAverageScore() {
        return averageScore.get();
    }

    public Double getAverageScoreOrElse(double defaultValue) {
        return averageScore.getOrElse(defaultValue);
    }

    public Double getMaxCourseScore() {
        return maxCourseScore.get();
    }

    public Double getMaxCourseScoreOrElse(double defaultValue) {
        return maxCourseScore.getOrElse(defaultValue);
    }

    public Double getMinCourseScore() {
        return minCourseScore.get();
    }

    public Double getMinCourseScoreOrElse(double defaultValue) {
        return minCourseScore.getOrElse(defaultValue);
    }

    public Boolean isPassed() {
        return passed.get();
    }

    public Boolean isPassedOrElse(boolean defaultValue) {
        return passed.getOrElse(defaultValue);
    }

    public int getYear() {
        return year;
    }

    public String getScholarshipSupportValue() {
        ScholarshipSupport support = scholarshipSupport.get();
        return support == null ? null : support.name();
    }

    public Map<String, SingleCourseStatistics> getCourseAverageScore() {
        return courseAverageScore;
    }
}
