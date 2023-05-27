package ae.encodelab.basics.data.csv.model.student.journal;

import ae.encodelab.basics.data.csv.model.uni.Course;

import java.util.List;

public record CourseMark(Course course, List<Integer> marks) {
}
