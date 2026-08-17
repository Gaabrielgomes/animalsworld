package animalsworld.api.domain.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByLogin(String login);

    Employee findBySsn(String ssn);

    Employee getEmployeeById(Long id);

    Page<Employee> findAllByActiveTrue(Pageable page);
}
