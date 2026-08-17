package animalsworld.api.domain.employee;

import animalsworld.api.domain.address.Address;
import animalsworld.api.domain.workData.WorkData;

public record EmployeeInfoDTO(
    Long id,
    String name,
    String email,
    String phone,
    String ssn,
    String login,
    WorkData workData,
    Address address,
    Boolean active
) {
    public EmployeeInfoDTO(Employee e) {
        this(
                e.getId(),
                e.getName(),
                e.getEmail(),
                e.getPhone(),
                e.getSsn(),
                e.getLogin(),
                e.getWorkData(),
                e.getAddress(),
                e.getActive()
        );
    }
}
