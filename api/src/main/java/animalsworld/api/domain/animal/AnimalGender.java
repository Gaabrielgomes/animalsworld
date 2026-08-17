package animalsworld.api.domain.animal;

public enum AnimalGender {
    FEMALE("female"),
    MALE("male");

    private final String animalGender;

    AnimalGender(String animalGender) { this.animalGender = animalGender; }

    public static AnimalGender fromString(String text) {
        for (AnimalGender ag : AnimalGender.values()) {
            if (ag.animalGender.equalsIgnoreCase(text)) {
                return ag;
            }
        }
        throw new IllegalArgumentException("This gender does not exist: " + text);
    }
}
