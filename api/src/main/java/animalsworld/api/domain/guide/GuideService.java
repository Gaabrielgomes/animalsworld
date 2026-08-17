package animalsworld.api.domain.guide;

import animalsworld.api.infra.security.AuthenticationService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GuideService {
    private final GuideRepository guideR;
    private final AuthenticationService authenticationS;
    private final PasswordEncoder passwordEncoder;

    public GuideService(GuideRepository guideR, AuthenticationService authenticationS, PasswordEncoder passwordEncoder) {
        this.guideR = guideR;
        this.authenticationS = authenticationS;
        this.passwordEncoder = passwordEncoder;
    }

    public GuideInfoDTO getGuideInfoById(Long id) {
        Guide g = guideR.getGuideById(id);
        if (g == null) {
            throw new EntityNotFoundException("Guide does not exist.");
        }
        return new GuideInfoDTO(g);
    }

    public Page<GuideListingDTO> listGuidesPerPage(Pageable page) {
        return guideR.findAllByActiveTrue(page).map(GuideListingDTO::new);
    }

    @Transactional
    public Guide signUp(GuideRegisterDTO dto) {
        if (guideR.findBySsn(dto.ssn()) != null) {
            throw new EntityExistsException("Guide with this SSN already exists.");
        }
        if (authenticationS.loginAlreadyTaken(dto.login())) {
            throw new EntityExistsException("This login is already taken.");
        }
        Guide g = new Guide(dto, passwordEncoder.encode(dto.password()));
        guideR.save(g);
        return g;
    }

    @Transactional
    public void updateGuideInfo(GuideUpdateDTO dto) {
        Guide g = guideR.getGuideById(dto.id());
        if (g == null) {
            throw new EntityNotFoundException("Guide does not exist.");
        }
        g.updateInfo(dto);
    }

    @Transactional
    public void inactiveGuideById(Long id) {
        Guide g = guideR.getGuideById(id);
        if (g == null) {
            throw new EntityNotFoundException("Guide does not exist.");
        }
        g.inactiveGuide();
    }
}
