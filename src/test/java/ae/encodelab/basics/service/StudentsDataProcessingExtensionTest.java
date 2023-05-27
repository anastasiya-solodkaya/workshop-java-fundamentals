package ae.encodelab.basics.service;

import ae.encodelab.basics.service.model.stats.ScholarshipSupport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mockStatic;

class StudentsDataProcessingExtensionTest {

    private StudentsDataProcessingExtension extension = new StudentsDataProcessingExtension();
    @Mock
    private Clock clock = Mockito.spy(Clock.class);
    private final static LocalDate LOCAL_DATE = LocalDate.of(2023, 5, 25);
    public static final ZoneId UTC = ZoneId.of("UTC");
    private static Clock fixedClock = Clock.fixed(LOCAL_DATE.atStartOfDay(UTC).toInstant(), UTC);


    public static Stream<Arguments> getCountOfRepeatingYears_Source() {
        return Stream.of(
                Arguments.of(2022, 1, 0),
                Arguments.of(2021, 1, 1),
                Arguments.of(2020, 1, 2),
                Arguments.of(2019, 1, 3),
                Arguments.of(2021, 2, 0),
                Arguments.of(2020, 2, 1),
                Arguments.of(2020, 3, 0),
                Arguments.of(2019, 3, 1),
                Arguments.of(2018, 3, 2),
                Arguments.of(2013, 3, 7)
        );
    }

    @ParameterizedTest
    @MethodSource("getCountOfRepeatingYears_Source")
    void getCountOfFailedYears(int yearOfAdmission, int actualYearOfEducation, int expectedResult) {
        Integer result = extension.getCountOfFailedYears(yearOfAdmission, actualYearOfEducation);
        assertThat(result, equalTo(expectedResult));
    }

    public static Stream<Arguments> isGraduatingThisYear_Source() {
        return Stream.of(
                Arguments.of(5, true),
                Arguments.of(4, false),
                Arguments.of(3, false),
                Arguments.of(2, false),
                Arguments.of(1, false)
        );
    }

    @ParameterizedTest
    @MethodSource("isGraduatingThisYear_Source")
    void isGraduatingThisYear(int actualYearOfEducation, boolean expectedResult) {
        Boolean result = extension.isGraduatingThisYear(actualYearOfEducation);
        assertThat(result, equalTo(expectedResult));
    }

    public static Stream<Arguments> isFresher_Source() {
        return Stream.of(
                Arguments.of(2022, true),
                Arguments.of(2021, false),
                Arguments.of(2020, false)
        );
    }

    @ParameterizedTest
    @MethodSource("isFresher_Source")
    void isFresher(int yearOfAdmission, boolean expectedResult) {
        Boolean result = extension.isFresher(yearOfAdmission);
        assertThat(result, equalTo(expectedResult));
    }

    public static Stream<Arguments> getCurrentAge_Source() {
        return Stream.of(
                Arguments.of(LocalDate.of(1999, 5, 25), 24),
                Arguments.of(LocalDate.of(2000, 5, 25), 23),
                Arguments.of(LocalDate.of(1999, 4, 25), 24),
                Arguments.of(LocalDate.of(1999, 5, 24), 24),
                Arguments.of(LocalDate.of(1999, 5, 26), 23),
                Arguments.of(LocalDate.of(1999, 5, 27), 23),
                Arguments.of(LocalDate.of(1999, 1, 1), 24)
        );
    }

    @ParameterizedTest
    @MethodSource("getCurrentAge_Source")
    void getCurrentAge(LocalDate dateOfBirth, int expectedResult) {
        MockedStatic<Clock> clockMockedStatic = mockStatic(Clock.class);
        try (clockMockedStatic) {
            clockMockedStatic.when(Clock::systemDefaultZone).thenReturn(fixedClock);
            Integer result = extension.calculateCurrentAge(dateOfBirth);
            assertThat(result, equalTo(expectedResult));
        }
    }

    public static Stream<Arguments> isBirthdayToday_Source() {
        return Stream.of(
                Arguments.of(LocalDate.of(1999, 5, 25), true),
                Arguments.of(LocalDate.of(2000, 5, 25), true),
                Arguments.of(LocalDate.of(1999, 4, 25), true),
                Arguments.of(LocalDate.of(1999, 5, 24), false),
                Arguments.of(LocalDate.of(1999, 5, 26), false),
                Arguments.of(LocalDate.of(1999, 5, 27), false),
                Arguments.of(LocalDate.of(1999, 1, 1), false)
        );
    }

    @ParameterizedTest
    @MethodSource("isBirthdayToday_Source")
    void isBirthdayToday(LocalDate dateOfBirth, boolean expectedResult) {
        MockedStatic<Clock> clockMockedStatic = mockStatic(Clock.class);
        try (clockMockedStatic) {
            clockMockedStatic.when(Clock::systemDefaultZone).thenReturn(fixedClock);
            Boolean result = extension.isBirthdayToday(dateOfBirth);
            assertThat(result, equalTo(expectedResult));
        }
    }

    public static Stream<Arguments> calculateAverageScore_Source() {
        return Stream.of(
                Arguments.of(new int[0], 0.0),
                Arguments.of(new int[]{1}, 1.0),
                Arguments.of(new int[]{1, 2}, 1.5),
                Arguments.of(new int[]{1, 3}, 2.0),
                Arguments.of(new int[]{1, 1, 1}, 1.0),
                Arguments.of(new int[]{1, 2, 2}, 1.666),
                Arguments.of(new int[]{10, 10, 10}, 10.0)
        );
    }

    @ParameterizedTest
    @MethodSource("calculateAverageScore_Source")
    void calculateAverageScore(int[] marks, Double expectedResult) {
        Double result = extension.calculateAverageScore(marks);
        if (expectedResult == null) {
            assertThat(result, nullValue());
        } else {
            assertThat(result, closeTo(expectedResult, 0.001));
        }
    }

    public static Stream<Arguments> findMaxCourseScore_Source() {
        return Stream.of(
                Arguments.of(new double[0], 0.0),
                Arguments.of(new double[]{1.0}, 1.0),
                Arguments.of(new double[]{1.0, 2.0}, 2.0),
                Arguments.of(new double[]{2.0, 1.0}, 2.0),
                Arguments.of(new double[]{2.0, 2.0}, 2.0),
                Arguments.of(new double[]{2.0, 2.0, 1.0, 3.7, 6.7, 2.1}, 6.7)
        );
    }

    @ParameterizedTest
    @MethodSource("findMaxCourseScore_Source")
    void findMaxCourseScore(double[] scores, Double expectedResult) {
        Double result = extension.findMaxCourseScore(scores);
        assertThat(result, equalTo(expectedResult));
    }

    public static Stream<Arguments> findMinCourseScore_Source() {
        return Stream.of(
                Arguments.of(new double[0], 0.0),
                Arguments.of(new double[]{1.0}, 1.0),
                Arguments.of(new double[]{1.0, 2.0}, 1.0),
                Arguments.of(new double[]{2.0, 1.0}, 1.0),
                Arguments.of(new double[]{2.0, 2.0}, 2.0),
                Arguments.of(new double[]{2.0, 2.0, 0.2, 3.7, 6.7, 2.1}, 0.2)
        );
    }

    @ParameterizedTest
    @MethodSource("findMinCourseScore_Source")
    void findMinCourseScore(double[] scores, Double expectedResult) {
        Double result = extension.findMinCourseScore(scores);
        assertThat(result, equalTo(expectedResult));
    }

    public static Stream<Arguments> checkCoursePassed_Source() {
        return Stream.of(
                Arguments.of(0.0, false),
                Arguments.of(5.0, false),
                Arguments.of(6.9, false),
                Arguments.of(6.99999, false),
                Arguments.of(7.0, true),
                Arguments.of(7.1, true),
                Arguments.of(9.0377474, true),
                Arguments.of(10.0, true)
        );
    }

    @ParameterizedTest
    @MethodSource("checkCoursePassed_Source")
    void checkCoursePassed(Double score, boolean expectedResult) {
        Boolean result = extension.checkCoursePassed(score);
        assertThat(result, equalTo(expectedResult));
    }

    public static Stream<Arguments> checkYearPassed_Source() {
        return Stream.of(
                Arguments.of(new boolean[0], true),
                Arguments.of(new boolean[]{true}, true),
                Arguments.of(new boolean[]{false}, false),
                Arguments.of(new boolean[]{false, true}, false),
                Arguments.of(new boolean[]{true, false}, false),
                Arguments.of(new boolean[]{true, true}, true),
                Arguments.of(new boolean[]{false, false}, false),
                Arguments.of(new boolean[]{false, false, false, false, false, false}, false),
                Arguments.of(new boolean[]{false, false, false, false, true, false}, false),
                Arguments.of(new boolean[]{true, true, true, true, true, true}, true),
                Arguments.of(new boolean[]{true, true, true, true, false, true}, false)
        );
    }

    @ParameterizedTest
    @MethodSource("checkYearPassed_Source")
    void checkYearPassed(boolean[] passes, boolean expectedResult) {
        Boolean result = extension.checkYearPassed(passes);
        assertThat(result, equalTo(expectedResult));
    }


    public static Stream<Arguments> getScholarshipSupport_Source() {
        return Stream.of(
                Arguments.of(false, 2.0, 2.0, ScholarshipSupport.NO_SUPPORT),
                Arguments.of(true, 7.0, 1.0, ScholarshipSupport.BASIC),
                Arguments.of(true, 8.9999999, 1.0, ScholarshipSupport.BASIC),
                Arguments.of(true, 9.0, 1.0, ScholarshipSupport.STANDARD),
                Arguments.of(true, 9.0, 7.0, ScholarshipSupport.STANDARD),
                Arguments.of(true, 9.0, 9.0, ScholarshipSupport.STANDARD),
                Arguments.of(true, 9.2, 9.2, ScholarshipSupport.SPECIAL),
                Arguments.of(true, 10.0, 9.2, ScholarshipSupport.SPECIAL),
                Arguments.of(true, 10.0, 10.0, ScholarshipSupport.SPECIAL)
        );
    }

    @ParameterizedTest
    @MethodSource("getScholarshipSupport_Source")
    void getScholarshipSupport(boolean previousYearResult, double previousYearAverage,
                               double previousYearMinimum, ScholarshipSupport expectedResult) {
        ScholarshipSupport result = extension.getScholarshipSupport(previousYearResult, previousYearAverage, previousYearMinimum);
        assertThat(result, equalTo(expectedResult));
    }
}