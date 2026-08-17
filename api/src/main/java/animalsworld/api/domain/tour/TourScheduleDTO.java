package animalsworld.api.domain.tour;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TourScheduleDTO(
   @NotNull
   Long guideId,
   @NotNull
   @Future
   LocalDateTime tourSchedule
) {}
