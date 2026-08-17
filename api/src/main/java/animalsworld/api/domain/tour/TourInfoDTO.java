package animalsworld.api.domain.tour;

import animalsworld.api.domain.visitor.VisitorListingDTO;

import java.time.LocalDateTime;
import java.util.List;

public record TourInfoDTO(
    Long id,
    Long guideId,
    String guideName,
    LocalDateTime tourSchedule,
    LocalDateTime tourEnd,
    Integer visitorsCount,
    Integer availableSpots,
    List<VisitorListingDTO> visitors,
    Boolean active
) {
    public TourInfoDTO(Tour t) {
        this(
                t.getId(),
                t.getGuide().getId(),
                t.getGuide().getName(),
                t.getTourSchedule(),
                t.getTourEnd(),
                t.getVisitors().size(),
                Tour.MAX_VISITORS - t.getVisitors().size(),
                t.getVisitors().stream().map(VisitorListingDTO::new).toList(),
                t.getActive()
        );
    }
}
