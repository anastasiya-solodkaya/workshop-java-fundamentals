package ae.encodelab.basics.service;

import ae.encodelab.basics.service.model.stats.ScholarshipSupport;
import org.apache.commons.lang3.NotImplementedException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.OptionalDouble;

public class StudentsDataProcessingExtension {

    public static final int LLAST_EDUCATION_YEAR = 5;
    public static final int PASS_MARK = 7;

    public int getCountOfRepeatingYears(int yearOfAdmission, int actualYearOfEducation) {
        // Check how many times student was repeating the same year of education (e.g. because of illness).
        // For example if student was admitted to university in 2021, they should be on 2d year now.
        // However, if actual year of education is one, this mean that student had to repeat the same year for a second
        // time.
        // In such case we can say student was repeating the same year __once__.


        throw new NotImplementedException();
    }

    public boolean isGraduatingThisYear(int actualYearOfEducation) {
        // Check if student is graduating this year (mean he is on 5th year of education)
        throw new NotImplementedException();
    }

    public boolean isFresher(int yearOfAdmission) {
        // Check if student is fresher (mean he was admitted to university year ago)
        throw new NotImplementedException();
    }

    public int getCurrentAge(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        // Check what is this person age.
        // Period.between() to find differences between dates and
        // Period#getYears() to determine actual amount of full years passed
        throw new NotImplementedException();
    }

    public boolean isBirthdayToday(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        // Check if today is person's birthday.
        // Period.between() to find differences between dates and
        // Period#getDays() to determine if today is exact date of birthday
        throw new NotImplementedException();

    }

    public double calculateAverageScore(int[] marks) {
        throw new NotImplementedException();

    }

    public double findMaxCourseScore(double[] coursesAverages) {
        throw new NotImplementedException();

    }

    public double findMinCourseScore(double[] coursesAverages) {
        throw new NotImplementedException();
    }

    public boolean checkCoursePassed(double avgScore) {
        throw new NotImplementedException();
    }

    public boolean checkYearPassed(boolean[] passes) {
        throw new NotImplementedException();
    }

    public ScholarshipSupport getScholarshipSupport(boolean previousYearResult, double previousYearAverage,
                                                    double previousYearMinimum) {

        throw new NotImplementedException();
    }
}
