package ae.encodelab.basics.data.csv.management;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public class TeacherJobCSVRecord {
    @CsvBindByName(column = "teacher_id")
    private long teacher;
    @CsvBindByName(column = "year_of_joining")
    private int fromYear;
    @CsvBindByName(column = "department")
    private String departmentCode;
    @CsvBindByName(column = "title")
    private String title;
}
