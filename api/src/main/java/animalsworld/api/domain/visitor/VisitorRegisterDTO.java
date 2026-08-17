package animalsworld.api.domain.visitor;

import animalsworld.api.domain.address.AddressDTO;
import animalsworld.api.domain.guide.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record VisitorRegisterDTO(
   @NotBlank
   String name,
   @NotBlank
   @Email
   String email,
   @NotBlank
   @Pattern(regexp = "^(\\+?1[-.\\s]?)?([2-9][0-9]{2})[-.\\s]?([2-9][0-9]{2})[-.\\s]?([0-9]{4})$")
   String phone,
   @NotBlank
   @Pattern(regexp = "^(?!000|666|9\\d{2})\\d{3}-(?!00)\\d{2}-(?!0000)\\d{4}$")
   String ssn,
   @NotNull
   @Past
   LocalDate birthDate,
   @NotNull
   Gender gender,
   @NotBlank
   @Size(min = 4, max = 50)
   String login,
   @NotBlank
   @Size(min = 8)
   String password,
   @NotNull
   @Valid
   AddressDTO addressDTO
) {}
