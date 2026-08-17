package animalsworld.api.domain.visitor;

import animalsworld.api.domain.address.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record VisitorUpdateDTO(
   @NotNull
   Long id,
   String name,
   @Email
   String email,
   String phone,
   String ssn,
   @Valid
   AddressDTO addressDTO
) {}
