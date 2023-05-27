package ae.encodelab.basics.data.csv.management;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public class DepartmentCSVRecord {
    @CsvBindByName
    private String code;
    @CsvBindByName
    private String name;
}
