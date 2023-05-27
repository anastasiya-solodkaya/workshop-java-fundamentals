package ae.encodelab.basics.data.csv.management;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
public class StudentJournalCSVRecord {
    @CsvBindByName(column = "id")
    private long studentId;
    @CsvBindByName(column = "course")
    private String courseCode;
    @CsvBindByName
    private int year;
    @CsvBindByName
    private int yearOfEducation;
    @CsvBindByName
    private String marks;
}
