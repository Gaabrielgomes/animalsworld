package animalsworld.api.domain.visitor;

import animalsworld.api.infra.security.AuthenticationService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class VisitorService {
    private final VisitorRepository visitorR;
    private final AuthenticationService authenticationS;
    private final PasswordEncoder passwordEncoder;

    public VisitorService(VisitorRepository visitorR, AuthenticationService authenticationS, PasswordEncoder passwordEncoder) {
        this.visitorR = visitorR;
        this.authenticationS = authenticationS;
        this.passwordEncoder = passwordEncoder;
    }

    public VisitorInfoDTO getVisitorInfoById(Long id) {
        Visitor v = visitorR.getVisitorById(id);
        if (v == null) {
            throw new EntityNotFoundException("Visitor does not exist.");
        }
        return new VisitorInfoDTO(v);
    }

    public Page<VisitorListingDTO> listVisitorsPerPage(Pageable page) {
        return visitorR.findAllByActiveTrue(page).map(VisitorListingDTO::new);
    }

    @Transactional
    public Visitor signUp(VisitorRegisterDTO dto) {
        if (visitorR.findBySsn(dto.ssn()) != null) {
            throw new EntityExistsException("Visitor with this SSN already exists.");
        }
        if (authenticationS.loginAlreadyTaken(dto.login())) {
            throw new EntityExistsException("This login is already taken.");
        }
        Visitor v = new Visitor(dto, passwordEncoder.encode(dto.password()));
        visitorR.save(v);
        return v;
    }

    @Transactional
    public void updateVisitorInfo(VisitorUpdateDTO dto) {
        Visitor v = visitorR.getVisitorById(dto.id());
        if (v == null) {
            throw new EntityNotFoundException("Visitor does not exist.");
        }
        v.updateInfo(dto);
    }

    @Transactional
    public void inactiveVisitorById(Long id) {
        Visitor v = visitorR.getVisitorById(id);
        if (v == null) {
            throw new EntityNotFoundException("Visitor does not exist.");
        }
        v.inactiveVisitor();
    }
}
