package animalsworld.api.domain.visitor;

import animalsworld.api.domain.address.Address;

public record VisitorInfoDTO(
    Long id,
    String name,
    String email,
    String phone,
    String ssn,
    String login,
    Address address,
    Boolean active
) {
    public VisitorInfoDTO(Visitor v) {
        this(
                v.getId(),
                v.getName(),
                v.getEmail(),
                v.getPhone(),
                v.getSsn(),
                v.getLogin(),
                v.getAddress(),
                v.getActive()
        );
    }
}
