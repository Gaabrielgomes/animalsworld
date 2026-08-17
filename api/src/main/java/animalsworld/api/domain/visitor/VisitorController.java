package animalsworld.api.domain.visitor;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("visitor")
public class VisitorController {

    private final VisitorService visitorS;

    public VisitorController(VisitorService visitorS) {
        this.visitorS = visitorS;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> visitorDetails(@PathVariable Long id) {
        VisitorInfoDTO v = visitorS.getVisitorInfoById(id);
        return ResponseEntity.ok(v);
    }

    @GetMapping("/activevisitors")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'GUIDE')")
    public ResponseEntity<?> listActiveVisitors(@PageableDefault(size = 5, sort = {"name"}) Pageable page) {
        Page<VisitorListingDTO> currentPage = visitorS.listVisitorsPerPage(page);
        return ResponseEntity.ok(currentPage);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid VisitorRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        Visitor v = visitorS.signUp(dto);
        var uri = uriBuilder.path("/visitor/{id}").buildAndExpand(v.getId()).toUri();
        return ResponseEntity.created(uri).body(new VisitorInfoDTO(v));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid VisitorUpdateDTO dto) {
        visitorS.updateVisitorInfo(dto);
        return ResponseEntity.ok("Info updated.");
    }

    @PatchMapping("/inactive/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> inactive(@PathVariable Long id) {
        visitorS.inactiveVisitorById(id);
        return ResponseEntity.ok("Visitor became inactive.");
    }
}
