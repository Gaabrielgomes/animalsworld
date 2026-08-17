package animalsworld.api.domain.animal;

public record AnimalListingDTO(
   Long id,
   String name,
   String specie,
   AnimalClass animalClass,
   AnimalGender gender,
   Boolean extinctionWarning
) {
    public AnimalListingDTO(Animal a) {
        this(
                a.getId(),
                a.getName(),
                a.getSpecie(),
                a.getAnimalClass(),
                a.getGender(),
                a.getExtinctionWarning()
        );
    }
}
