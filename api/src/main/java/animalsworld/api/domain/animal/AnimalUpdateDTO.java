package animalsworld.api.domain.animal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record AnimalUpdateDTO(
   @NotNull
   Long id,
   String name,
   @PastOrPresent
   LocalDate deathDate,
   String race,
   String specie,
   AnimalClass animalClass,
   Boolean extinctionWarning
) {
    public AnimalUpdateDTO(Animal a) {
        this(
                a.getId(),
                a.getName(),
                a.getDeathDate(),
                a.getRace(),
                a.getSpecie(),
                a.getAnimalClass(),
                a.getExtinctionWarning()
        );
    }
}
