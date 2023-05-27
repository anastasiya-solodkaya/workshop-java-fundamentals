package ae.encodelab.basics.data.csv.management;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public class StudentCSVRecord extends PersonCSVRecord {
    @CsvBindByName(column = "registered")
    private int yearOfRegistration;
    @CsvBindByName(column = "year")
    private int yearOfEducation;
    @CsvBindByName(column = "department")
    private String departmentCode;
}
