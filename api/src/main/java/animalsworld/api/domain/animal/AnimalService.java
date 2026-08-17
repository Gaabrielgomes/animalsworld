package animalsworld.api.domain.animal;

import animalsworld.api.infra.exception.BusinessRuleException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {
    private final AnimalRepository animalR;

    public AnimalService(AnimalRepository animalR) { this.animalR = animalR; }

    public AnimalInfoDTO getAnimalInfoById(Long id) {
        Animal a = animalR.getAnimalById(id);
        if (a == null) {
            throw new EntityNotFoundException("Animal does not exist.");
        }
        return new AnimalInfoDTO(a);
    }

    public Page<AnimalListingDTO> listAnimalsPerPage(Pageable page) {
        return animalR.findAllByActiveTrue(page).map(AnimalListingDTO::new);
    }

    @Transactional
    public Animal register(AnimalRegisterDTO dto) {
        Animal mother = findParent(dto.motherId(), AnimalGender.FEMALE, "mother");
        Animal father = findParent(dto.fatherId(), AnimalGender.MALE, "father");

        Animal a = new Animal(dto, mother, father);
        animalR.save(a);
        return a;
    }

    @Transactional
    public void updateAnimalInfo(AnimalUpdateDTO dto) {
        Animal a = animalR.getAnimalById(dto.id());
        if (a == null) {
            throw new EntityNotFoundException("Animal does not exist.");
        }
        if (dto.deathDate() != null && a.getBirthDate() != null && dto.deathDate().isBefore(a.getBirthDate())) {
            throw new BusinessRuleException("Death date cannot be before birth date.");
        }
        a.updateAnimal(dto);
    }

    @Transactional
    public void inactiveAnimalById(Long id) {
        Animal a = animalR.getAnimalById(id);
        if (a == null) {
            throw new EntityNotFoundException("Animal does not exist.");
        }
        a.inactiveAnimal();
    }

    private Animal findParent(Long id, AnimalGender expectedGender, String parentName) {
        if (id == null) {
            return null;
        }
        Animal parent = animalR.getAnimalById(id);
        if (parent == null) {
            throw new EntityNotFoundException("The " + parentName + " informed is not registered.");
        }
        if (parent.getGender() != expectedGender) {
            throw new BusinessRuleException("The animal informed as " + parentName + " does not match the expected gender.");
        }
        return parent;
    }
}
