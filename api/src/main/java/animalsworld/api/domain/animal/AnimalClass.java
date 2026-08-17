package animalsworld.api.domain.animal;

public enum AnimalClass {
    MAMMAL("mammal"),
    FISH("fish"),
    BIRD("bird"),
    REPTILE("reptile"),
    AMPHIBIAN("amphibian");

    private final String animalClass;

    AnimalClass(String animalClass) { this.animalClass = animalClass; }

    public static AnimalClass fromString(String text) {
        for(AnimalClass ac : AnimalClass.values()) {
            if (ac.animalClass.equalsIgnoreCase(text)) {
                return ac;
            }
        }
        throw new IllegalArgumentException("This animal class isn't registered: " + text);
    }
}
