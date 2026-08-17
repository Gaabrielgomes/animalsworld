package animalsworld.api.infra.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public interface SystemUser extends UserDetails {

    UserRole getRole();

    Boolean getActive();

    @Override
    default Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().getAuthority()));
    }

    @Override
    default boolean isEnabled() { return Boolean.TRUE.equals(getActive()); }
}
