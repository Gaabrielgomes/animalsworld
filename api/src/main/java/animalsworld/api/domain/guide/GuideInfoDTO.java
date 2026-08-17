package animalsworld.api.domain.guide;

import animalsworld.api.domain.address.Address;
import animalsworld.api.domain.workData.WorkData;

public record GuideInfoDTO(
    Long id,
    String name,
    String email,
    String phone,
    String ssn,
    String login,
    WorkData workData,
    Address address,
    Boolean active
) {
    public GuideInfoDTO(Guide g) {
        this(
                g.getId(),
                g.getName(),
                g.getEmail(),
                g.getPhone(),
                g.getSsn(),
                g.getLogin(),
                g.getWorkData(),
                g.getAddress(),
                g.getActive()
        );
    }
}
