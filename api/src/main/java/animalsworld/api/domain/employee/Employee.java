package animalsworld.api.domain.employee;

import animalsworld.api.domain.address.Address;
import animalsworld.api.domain.guide.Gender;
import animalsworld.api.domain.workData.WorkData;
import animalsworld.api.infra.security.SystemUser;
import animalsworld.api.infra.security.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "employees")
@Entity(name = "Employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Employee implements SystemUser {
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
    private WorkData workData;

    @Embedded
    private Address address;

    private Boolean active = Boolean.TRUE;

    public Employee(EmployeeRegisterDTO dto, String encodedPassword) {
        this.name = dto.name();
        this.email = dto.email();
        this.phone = dto.phone();
        this.ssn = dto.ssn();
        this.birthDate = dto.birthDate();
        this.gender = dto.gender();
        this.login = dto.login();
        this.password = encodedPassword;
        this.workData = new WorkData(dto.workDataDTO());
        this.address = new Address(dto.addressDTO());
        this.active = Boolean.TRUE;
    }

    public void updateInfo(EmployeeUpdateDTO dto) {
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

    public void inactiveEmployee() { this.active = Boolean.FALSE; }

    @Override
    public UserRole getRole() { return UserRole.EMPLOYEE; }

    @Override
    public String getUsername() { return this.login; }

    @Override
    public String getPassword() { return this.password; }
}
