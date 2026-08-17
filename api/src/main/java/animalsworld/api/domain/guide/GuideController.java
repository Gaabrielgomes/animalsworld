package animalsworld.api.domain.guide;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("guide")
public class GuideController {

    private final GuideService guideS;

    public GuideController(GuideService guideS) {
        this.guideS = guideS;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> guideDetails(@PathVariable Long id) {
        GuideInfoDTO g = guideS.getGuideInfoById(id);
        return ResponseEntity.ok(g);
    }

    @GetMapping
    public ResponseEntity<?> listActiveGuides(@PageableDefault(size = 5, sort = {"name"}) Pageable page) {
        Page<GuideListingDTO> currentPage = guideS.listGuidesPerPage(page);
        return ResponseEntity.ok(currentPage);
    }

    @PostMapping("/registerguide")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> registerGuide(@RequestBody @Valid GuideRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        Guide g = guideS.signUp(dto);
        var uri = uriBuilder.path("/guide/{id}").buildAndExpand(g.getId()).toUri();
        return ResponseEntity.created(uri).body(new GuideInfoDTO(g));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> update(@RequestBody @Valid GuideUpdateDTO dto) {
        guideS.updateGuideInfo(dto);
        return ResponseEntity.ok("Info updated.");
    }

    @PatchMapping("/inactive/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> inactive(@PathVariable Long id) {
        guideS.inactiveGuideById(id);
        return ResponseEntity.ok("Guide became inactive.");
    }
}
