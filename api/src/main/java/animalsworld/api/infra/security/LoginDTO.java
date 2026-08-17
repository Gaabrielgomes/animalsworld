package animalsworld.api.infra.security;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
   @NotBlank
   String login,
   @NotBlank
   String password
) {}
