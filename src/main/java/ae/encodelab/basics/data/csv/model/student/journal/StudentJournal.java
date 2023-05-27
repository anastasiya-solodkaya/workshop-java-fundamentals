package ae.encodelab.basics.data.csv.model.student.journal;

import java.util.List;

public record StudentJournal(long studentId, List<StudentJournalRecord> records) {
}
