package animalsworld.api.infra.security;

import animalsworld.api.domain.employee.EmployeeRepository;
import animalsworld.api.domain.guide.GuideRepository;
import animalsworld.api.domain.visitor.VisitorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final EmployeeRepository employeeR;
    private final GuideRepository guideR;
    private final VisitorRepository visitorR;

    public AuthenticationService(EmployeeRepository employeeR, GuideRepository guideR, VisitorRepository visitorR) {
        this.employeeR = employeeR;
        this.guideR = guideR;
        this.visitorR = visitorR;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        UserDetails employee = employeeR.findByLogin(login);
        if (employee != null) {
            return employee;
        }

        UserDetails guide = guideR.findByLogin(login);
        if (guide != null) {
            return guide;
        }

        UserDetails visitor = visitorR.findByLogin(login);
        if (visitor != null) {
            return visitor;
        }

        throw new UsernameNotFoundException("Login or password is invalid.");
    }

    public boolean loginAlreadyTaken(String login) {
        return employeeR.findByLogin(login) != null
                || guideR.findByLogin(login) != null
                || visitorR.findByLogin(login) != null;
    }
}
