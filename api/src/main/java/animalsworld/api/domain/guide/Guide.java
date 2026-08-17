package animalsworld.api.domain.guide;

import animalsworld.api.domain.address.Address;
import animalsworld.api.domain.workData.WorkData;
import animalsworld.api.infra.security.SystemUser;
import animalsworld.api.infra.security.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name="guides")
@Entity(name="Guide")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Guide implements SystemUser {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    private WorkData workData;

    @Embedded
    private Address address;

    private Boolean active = Boolean.TRUE;

    public Guide(GuideRegisterDTO g, String encodedPassword) {
        this.name = g.name();
        this.email = g.email();
        this.phone = g.phone();
        this.ssn = g.ssn();
        this.birthDate = g.birthDate();
        this.gender = g.gender();
        this.login = g.login();
        this.password = encodedPassword;
        this.workData = new WorkData(g.workDataDTO());
        this.address = new Address(g.addressDTO());
        this.active = Boolean.TRUE;
    }

    public void updateInfo(GuideUpdateDTO dto) {
        if (dto.name() != null) {
            this.name = dto.name();
        }

        if (dto.email() != null) {
            this.email = dto.email();
        }

        if (dto.phone() != null) {
            this.phone = dto.phone();
        }

        if (dto.addressDTO() != null) {
            this.address.updateAddress(dto.addressDTO());
        }

        if (dto.workDataDTO() != null) {
            this.workData.updateWorkData(dto.workDataDTO());
        }
    }

    public void inactiveGuide() { this.active = Boolean.FALSE; }

    @Override
    public UserRole getRole() { return UserRole.GUIDE; }

    @Override
    public String getUsername() { return this.login; }

    @Override
    public String getPassword() { return this.password; }
}
