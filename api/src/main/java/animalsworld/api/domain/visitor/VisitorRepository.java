package animalsworld.api.domain.visitor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    Visitor findByLogin(String login);

    Visitor findBySsn(String ssn);

    Visitor getVisitorById(Long id);

    Page<Visitor> findAllByActiveTrue(Pageable page);
}
