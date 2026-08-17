package animalsworld.api.domain.animal;

import java.time.LocalDate;

public record AnimalInfoDTO(
    Long id,
    String name,
    LocalDate birthDate,
    LocalDate deathDate,
    Long motherId,
    Long fatherId,
    AnimalGender gender,
    String race,
    String specie,
    AnimalClass animalClass,
    Boolean extinctionWarning,
    Boolean active
) {
    public AnimalInfoDTO(Animal a) {
        this(
                a.getId(),
                a.getName(),
                a.getBirthDate(),
                a.getDeathDate(),
                a.getMother() != null ? a.getMother().getId() : null,
                a.getFather() != null ? a.getFather().getId() : null,
                a.getGender(),
                a.getRace(),
                a.getSpecie(),
                a.getAnimalClass(),
                a.getExtinctionWarning(),
                a.getActive()
        );
    }
}
