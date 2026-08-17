package animalsworld.api.domain.guide;

public enum Gender {
    MAN("man"),
    WOMAN("woman");

    private final String gender;

    Gender(String gender) { this.gender = gender; }

    public static Gender fromString(String text) {
        for (Gender g : Gender.values()) {
            if (g.gender.equalsIgnoreCase(text)) {
                return g;
            }
        }
        throw new IllegalArgumentException("This gender does not exist: " + text);
    }
}
