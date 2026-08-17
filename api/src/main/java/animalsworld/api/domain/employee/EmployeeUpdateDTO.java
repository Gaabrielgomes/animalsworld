package animalsworld.api.domain.employee;

import animalsworld.api.domain.address.AddressDTO;
import animalsworld.api.domain.workData.WorkDataDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmployeeUpdateDTO(
   @NotNull
   Long id,
   String name,
   @Email
   String email,
   String phone,
   @Valid
   AddressDTO addressDTO,
   @Valid
   WorkDataDTO workDataDTO
) {
    public EmployeeUpdateDTO(Employee e) {
        this(
                e.getId(),
                e.getName(),
                e.getEmail(),
                e.getPhone(),
                new AddressDTO(e.getAddress()),
                new WorkDataDTO(e.getWorkData())
        );
    }
}
