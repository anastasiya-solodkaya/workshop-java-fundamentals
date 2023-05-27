package ae.encodelab.basics.service.model;

import ae.encodelab.basics.service.model.journals.StudentJournalYearRecord;
import ae.encodelab.basics.service.model.stats.BasicStudentStatsitics;
import ae.encodelab.basics.service.model.stats.SingleYearStatistics;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StudentDetailedInfo {

    private final long id;
    private final int currentYearOfEducation;
    private final int yearStarted;
    private final String departmentCode;
    private final String departmentName;
    private final BasicStudentStatsitics stats;
    private final PersonalInfo personalInfo;
    private final List<SingleYearStatistics> marksStatistics;
    private final List<StudentJournalYearRecord> journal;

    public double getAverage(int year, String courseCode){
        SingleYearStatistics stats = getYearStats(year);
        if(stats == null){
            return 0;
        }

        return stats.getCourseAverageScore().get(courseCode).getAverage();
    }
    public boolean isPassed(int year, String courseCode){
        SingleYearStatistics stats = getYearStats(year);
        if(stats == null){
            return false;
        }

        return stats.getCourseAverageScore().get(courseCode).isPassed();
    }

    public SingleYearStatistics getYearStats(int year) {
        return  marksStatistics.stream().filter(t -> t.getYear() == year).findFirst().orElse(null);
    }
}
