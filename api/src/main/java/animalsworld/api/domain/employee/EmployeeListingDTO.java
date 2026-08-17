package animalsworld.api.domain.employee;

import animalsworld.api.domain.workData.Specialization;
import animalsworld.api.domain.workData.WorkingShift;
import animalsworld.api.domain.workData.WorkingSituation;

public record EmployeeListingDTO(
    Long id,
    String name,
    String email,
    WorkingShift workingShift,
    WorkingSituation workingSituation,
    Specialization specialization
) {
    public EmployeeListingDTO(Employee e) {
        this(
                e.getId(),
                e.getName(),
                e.getEmail(),
                e.getWorkData().getWorkingShift(),
                e.getWorkData().getWorkingSituation(),
                e.getWorkData().getSpecialization()
        );
    }
}
