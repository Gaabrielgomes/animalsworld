package animalsworld.api.infra.security;

public record AuthenticatedUserDTO(
   String login,
   UserRole role
) {
    public AuthenticatedUserDTO(SystemUser u) {
        this(
                u.getUsername(),
                u.getRole()
        );
    }
}
