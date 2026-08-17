package animalsworld.api.domain.guide;

import animalsworld.api.domain.workData.Specialization;
import animalsworld.api.domain.workData.WorkingShift;
import animalsworld.api.domain.workData.WorkingSituation;

public record GuideListingDTO(
    Long id,
    String name,
    String email,
    WorkingShift workingShift,
    WorkingSituation workingSituation,
    Specialization specialization
) {
    public GuideListingDTO(Guide g) {
        this(
                g.getId(),
                g.getName(),
                g.getEmail(),
                g.getWorkData().getWorkingShift(),
                g.getWorkData().getWorkingSituation(),
                g.getWorkData().getSpecialization()
        );
    }
}
