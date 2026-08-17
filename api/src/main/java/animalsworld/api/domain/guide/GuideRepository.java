package animalsworld.api.domain.guide;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<Guide, Long> {
    Guide findByLogin(String login);

    Guide findBySsn(String ssn);

    Guide getGuideById(Long id);

    Page<Guide> findAllByActiveTrue(Pageable pageable);
}
