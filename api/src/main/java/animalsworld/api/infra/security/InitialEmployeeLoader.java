package animalsworld.api.infra.security;

import animalsworld.api.domain.employee.Employee;
import animalsworld.api.domain.employee.EmployeeRepository;
import animalsworld.api.domain.workData.ContractType;
import animalsworld.api.domain.workData.Specialization;
import animalsworld.api.domain.workData.WorkData;
import animalsworld.api.domain.workData.WorkDataDTO;
import animalsworld.api.domain.workData.WorkingShift;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialEmployeeLoader implements ApplicationRunner {

    private final EmployeeRepository employeeR;
    private final PasswordEncoder passwordEncoder;

    @Value("${animalsworld.security.initial-employee.login}")
    private String login;

    @Value("${animalsworld.security.initial-employee.password}")
    private String password;

    @Value("${animalsworld.security.initial-employee.name}")
    private String name;

    @Value("${animalsworld.security.initial-employee.email}")
    private String email;

    @Value("${animalsworld.security.initial-employee.ssn}")
    private String ssn;

    public InitialEmployeeLoader(EmployeeRepository employeeR, PasswordEncoder passwordEncoder) {
        this.employeeR = employeeR;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (employeeR.count() > 0) {
            return;
        }

        Employee e = new Employee();
        e.setName(name);
        e.setEmail(email);
        e.setSsn(ssn);
        e.setLogin(login);
        e.setPassword(passwordEncoder.encode(password));
        e.setWorkData(new WorkData(new WorkDataDTO(
                Specialization.MAMMALS,
                ContractType.FULLTIME,
                WorkingShift.FIRSTSHIFT,
                null
        )));
        e.setActive(Boolean.TRUE);
        employeeR.save(e);
    }
}
