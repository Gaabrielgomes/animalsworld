package animalsworld.api.domain.animal;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("animal")
public class AnimalController {

    private final AnimalService animalS;

    public AnimalController(AnimalService animalS) {
        this.animalS = animalS;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> animalDetails(@PathVariable Long id) {
        AnimalInfoDTO a = animalS.getAnimalInfoById(id);
        return ResponseEntity.ok(a);
    }

    @GetMapping
    public ResponseEntity<?> listActiveAnimals(@PageableDefault(size = 5, sort = {"name"}) Pageable page) {
        Page<AnimalListingDTO> currentPage = animalS.listAnimalsPerPage(page);
        return ResponseEntity.ok(currentPage);
    }

    @PostMapping("/registeranimal")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> registerAnimal(@RequestBody @Valid AnimalRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        Animal a = animalS.register(dto);
        var uri = uriBuilder.path("/animal/{id}").buildAndExpand(a.getId()).toUri();
        return ResponseEntity.created(uri).body(new AnimalInfoDTO(a));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> update(@RequestBody @Valid AnimalUpdateDTO dto) {
        animalS.updateAnimalInfo(dto);
        return ResponseEntity.ok("Info updated.");
    }

    @PatchMapping("/inactive/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> inactive(@PathVariable Long id) {
        animalS.inactiveAnimalById(id);
        return ResponseEntity.ok("Animal became inactive.");
    }
}
