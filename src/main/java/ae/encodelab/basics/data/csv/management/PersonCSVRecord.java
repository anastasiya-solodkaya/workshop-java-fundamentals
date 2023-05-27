package ae.encodelab.basics.data.csv.management;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@NoArgsConstructor
@ToString
@Getter
public class PersonCSVRecord {
    @CsvBindByName
    private long id;
    @CsvBindByName(column = "first_name")
    private String firstName;
    @CsvBindByName(column = "last_name")
    private String lastName;
    @CsvBindByName
    private boolean gender;
    @CsvDate(value = "MM/dd/yyyy")
    @CsvBindByName(column = "birth_date")
    private LocalDate birthDate;
}
