package animalsworld.api.domain.tour;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TourUpdateDTO(
   @NotNull
   Long id,
   Long guideId,
   @Future
   LocalDateTime tourSchedule
) {}
