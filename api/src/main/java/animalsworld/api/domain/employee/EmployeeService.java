package animalsworld.api.domain.employee;

import animalsworld.api.infra.security.AuthenticationService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeR;
    private final AuthenticationService authenticationS;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeR, AuthenticationService authenticationS, PasswordEncoder passwordEncoder) {
        this.employeeR = employeeR;
        this.authenticationS = authenticationS;
        this.passwordEncoder = passwordEncoder;
    }

    public EmployeeInfoDTO getEmployeeInfoById(Long id) {
        Employee e = employeeR.getEmployeeById(id);
        if (e == null) {
            throw new EntityNotFoundException("Employee does not exist.");
        }
        return new EmployeeInfoDTO(e);
    }

    public Page<EmployeeListingDTO> listEmployeesPerPage(Pageable page) {
        return employeeR.findAllByActiveTrue(page).map(EmployeeListingDTO::new);
    }

    @Transactional
    public Employee signUp(EmployeeRegisterDTO dto) {
        if (employeeR.findBySsn(dto.ssn()) != null) {
            throw new EntityExistsException("Employee with this SSN already exists.");
        }
        if (authenticationS.loginAlreadyTaken(dto.login())) {
            throw new EntityExistsException("This login is already taken.");
        }
        Employee e = new Employee(dto, passwordEncoder.encode(dto.password()));
        employeeR.save(e);
        return e;
    }

    @Transactional
    public void updateEmployeeInfo(EmployeeUpdateDTO dto) {
        Employee e = employeeR.getEmployeeById(dto.id());
        if (e == null) {
            throw new EntityNotFoundException("Employee does not exist.");
        }
        e.updateInfo(dto);
    }

    @Transactional
    public void inactiveEmployeeById(Long id) {
        Employee e = employeeR.getEmployeeById(id);
        if (e == null) {
            throw new EntityNotFoundException("Employee does not exist.");
        }
        e.inactiveEmployee();
    }
}
