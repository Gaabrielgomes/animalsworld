package animalsworld.api.domain.tour;

import java.time.LocalDateTime;

public record TourListingDTO(
   Long id,
   String guideName,
   LocalDateTime tourSchedule,
   LocalDateTime tourEnd,
   Integer visitorsCount,
   Integer availableSpots
) {
    public TourListingDTO(Tour t) {
        this(
                t.getId(),
                t.getGuide().getName(),
                t.getTourSchedule(),
                t.getTourEnd(),
                t.getVisitors().size(),
                Tour.MAX_VISITORS - t.getVisitors().size()
        );
    }
}
