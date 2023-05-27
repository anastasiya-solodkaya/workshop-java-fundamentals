package ae.encodelab.basics.service;

import ae.encodelab.basics.service.model.stats.ScholarshipSupport;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

public class StudentsDataProcessingExtension {

    public static final int LLAST_EDUCATION_YEAR = 5;
    public static final int PASS_MARK = 7;

    public Integer getCountOfRepeatingYears(int yearOfAdmission, int actualYearOfEducation) {
        // Check how many times student was repeating the same year of education (e.g. because of illness).
        // For example if student was admitted to university in 2021, they should be on 2d year now.
        // However, if actual year of education is one, this mean that student had to repeat the same year for a second
        // time.
        // In such case we can say student was repeating the same year __once__.


        return (LocalDate.now().getYear() - yearOfAdmission) - actualYearOfEducation;
    }

    public Boolean isGraduatingThisYear(int actualYearOfEducation) {
        // Check if student is graduating this year (mean he is on 5th year of education)
        return actualYearOfEducation == LLAST_EDUCATION_YEAR;
    }

    public Boolean isFresher(int yearOfAdmission) {
        // Check if student is fresher (mean he was admitted to university year ago)
        return yearOfAdmission == LocalDate.now().getYear() - 1;
    }

    public Integer getCurrentAge(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        // Check what is this person age.
        // Period.between() to find differences between dates and
        // Period#getYears() to determine actual amount of full years passed
        return Period.between(dateOfBirth, now).getYears();
    }

    public Boolean isBirthdayToday(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        // Check if today is person's birthday.
        // Period.between() to find differences between dates and
        // Period#getDays() to determine if today is exact date of birthday
        return Period.between(now, dateOfBirth).getDays() == 0;
    }

    public Double calculateAverageScore(int[] marks) {
        return Arrays.stream(marks).average().orElse(0);
    }

    public Double findMaxCourseScore(double[] coursesAverages) {
        return Arrays.stream(coursesAverages).max().orElse(0);
    }

    public Double findMinCourseScore(double[] coursesAverages) {
        return Arrays.stream(coursesAverages).min().orElse(0);
    }

    public Boolean checkCoursePassed(double avgScore) {
        return avgScore >= PASS_MARK;
    }

    public Boolean checkYearPassed(boolean[] passes) {
        for (boolean pass : passes) {
            if (!pass) {
                return false;
            }
        }
        return true;
    }

    public ScholarshipSupport getScholarshipSupport(boolean previousYearResult, double previousYearAverage,
                                                    double previousYearMinimum) {

        if (!previousYearResult){
            return ScholarshipSupport.NO_SUPPORT;
        } else if(previousYearAverage < 9.0) {
            return ScholarshipSupport.BASIC;
        } else if(previousYearMinimum > 9.0) {
            return ScholarshipSupport.SPECIAL;
        } else {
            return ScholarshipSupport.STANDARD;
        }
    }
}
