package animalsworld.api.domain.tour;

import jakarta.validation.constraints.NotNull;

public record TourVisitorDTO(
   @NotNull
   Long tourId,
   @NotNull
   Long visitorId
) {}
