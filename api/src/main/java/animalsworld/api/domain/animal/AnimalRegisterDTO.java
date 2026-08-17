package animalsworld.api.domain.animal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record AnimalRegisterDTO(
   @NotBlank
   String name,
   @NotNull
   @PastOrPresent
   LocalDate birthDate,
   Long motherId,
   Long fatherId,
   @NotNull
   AnimalGender gender,
   String race,
   @NotBlank
   String specie,
   @NotNull
   AnimalClass animalClass,
   @NotNull
   Boolean extinctionWarning
) {}
