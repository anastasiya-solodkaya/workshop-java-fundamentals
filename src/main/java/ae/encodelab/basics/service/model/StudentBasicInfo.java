package ae.encodelab.basics.service.model;


import ae.encodelab.basics.service.model.stats.BasicStudentStatsitics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentBasicInfo {
    private final long id;
    private final int currentYearOfEducation;
    private final int yearStarted;
    private final String departmentName;
    private final String departmentCode;
    private final BasicStudentStatsitics stats;
    private final PersonalInfo personalInfo;

}


