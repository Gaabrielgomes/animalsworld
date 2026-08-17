package animalsworld.api.domain.visitor;

import animalsworld.api.domain.address.Address;
import animalsworld.api.domain.guide.Gender;
import animalsworld.api.infra.security.SystemUser;
import animalsworld.api.infra.security.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "visitors")
@Entity(name = "Visitor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Visitor implements SystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String ssn;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String login;

    private String password;

    @Embedded
    private Address address;

    private Boolean active = Boolean.TRUE;

    public Visitor(VisitorRegisterDTO dto, String encodedPassword) {
        this.name = dto.name();
        this.email = dto.email();
        this.phone = dto.phone();
        this.ssn = dto.ssn();
        this.birthDate = dto.birthDate();
        this.gender = dto.gender();
        this.login = dto.login();
        this.password = encodedPassword;
        this.address = new Address(dto.addressDTO());
        this.active = Boolean.TRUE;
    }

    public void updateInfo(VisitorUpdateDTO dto) {
        if (dto.name() != null) {
            this.name = dto.name();
        }

        if (dto.email() != null) {
            this.email = dto.email();
        }

        if (dto.phone() != null) {
            this.phone = dto.phone();
        }

        if (dto.ssn() != null) {
            this.ssn = dto.ssn();
        }

        if (dto.addressDTO() != null) {
            this.address.updateAddress(dto.addressDTO());
        }
    }

    public void inactiveVisitor() { this.active = Boolean.FALSE; }

    @Override
    public UserRole getRole() { return UserRole.VISITOR; }

    @Override
    public String getUsername() { return this.login; }

    @Override
    public String getPassword() { return this.password; }
}
