package ae.encodelab.basics.data.csv.model.student.journal;

import java.util.List;

public record StudentJournalRecord (int year, int yearOfEducation, List<CourseMark> marks){
}
