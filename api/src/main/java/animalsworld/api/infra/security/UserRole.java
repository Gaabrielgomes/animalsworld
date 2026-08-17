package animalsworld.api.infra.security;

public enum UserRole {
    EMPLOYEE("employee"),
    GUIDE("guide"),
    VISITOR("visitor");

    private final String role;

    UserRole(String role) { this.role = role; }

    public String getAuthority() { return "ROLE_" + this.name(); }

    public static UserRole fromString(String text) {
        for (UserRole ur : UserRole.values()) {
            if (ur.role.equalsIgnoreCase(text)) {
                return ur;
            }
        }
        throw new IllegalArgumentException("This role does not exist: " + text);
    }
}
