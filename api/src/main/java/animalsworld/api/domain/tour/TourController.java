package animalsworld.api.domain.tour;

import animalsworld.api.domain.visitor.Visitor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("tour")
public class TourController {

    private final TourService tourS;

    public TourController(TourService tourS) {
        this.tourS = tourS;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> tourDetails(@PathVariable Long id) {
        TourInfoDTO t = tourS.getTourInfoById(id);
        return ResponseEntity.ok(t);
    }

    @GetMapping
    public ResponseEntity<?> listActiveTours(@PageableDefault(size = 5, sort = {"tourSchedule"}) Pageable page) {
        Page<TourListingDTO> currentPage = tourS.listToursPerPage(page);
        return ResponseEntity.ok(currentPage);
    }

    @GetMapping("/guide/{guideId}")
    public ResponseEntity<?> listToursByGuide(@PathVariable Long guideId,
                                              @PageableDefault(size = 5, sort = {"tourSchedule"}) Pageable page) {
        Page<TourListingDTO> currentPage = tourS.listToursByGuidePerPage(guideId, page);
        return ResponseEntity.ok(currentPage);
    }

    @PostMapping("/scheduletour")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'GUIDE')")
    public ResponseEntity<?> scheduleTour(@RequestBody @Valid TourScheduleDTO dto, UriComponentsBuilder uriBuilder) {
        Tour t = tourS.scheduleTour(dto);
        var uri = uriBuilder.path("/tour/{id}").buildAndExpand(t.getId()).toUri();
        return ResponseEntity.created(uri).body(new TourInfoDTO(t));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'GUIDE')")
    public ResponseEntity<?> update(@RequestBody @Valid TourUpdateDTO dto) {
        tourS.updateTour(dto);
        return ResponseEntity.ok("Tour updated.");
    }

    @PostMapping("/addvisitor")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'GUIDE')")
    public ResponseEntity<?> addVisitor(@RequestBody @Valid TourVisitorDTO dto) {
        tourS.addVisitorToTour(dto.tourId(), dto.visitorId());
        return ResponseEntity.ok("Visitor added to the tour group.");
    }

    @DeleteMapping("/removevisitor")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'GUIDE')")
    public ResponseEntity<?> removeVisitor(@RequestBody @Valid TourVisitorDTO dto) {
        tourS.removeVisitorFromTour(dto.tourId(), dto.visitorId());
        return ResponseEntity.ok("Visitor removed from the tour group.");
    }

    @PostMapping("/join/{id}")
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<?> join(@PathVariable Long id, @AuthenticationPrincipal Visitor v) {
        tourS.addVisitorToTour(id, v.getId());
        return ResponseEntity.ok("You joined the tour group.");
    }

    @DeleteMapping("/leave/{id}")
    @PreAuthorize("hasRole('VISITOR')")
    public ResponseEntity<?> leave(@PathVariable Long id, @AuthenticationPrincipal Visitor v) {
        tourS.removeVisitorFromTour(id, v.getId());
        return ResponseEntity.ok("You left the tour group.");
    }

    @PatchMapping("/cancel/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'GUIDE')")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        tourS.cancelTourById(id);
        return ResponseEntity.ok("Tour was canceled.");
    }
}
