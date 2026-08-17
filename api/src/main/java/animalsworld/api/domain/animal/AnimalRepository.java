package animalsworld.api.domain.animal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Animal getAnimalById(Long id);

    Page<Animal> findAllByActiveTrue(Pageable page);
}
