package animalsworld.api.domain.visitor;

public record VisitorListingDTO(
   Long id,
   String name,
   String email,
   String ssn
) {
    public VisitorListingDTO(Visitor v) {
        this(
                v.getId(),
                v.getName(),
                v.getEmail(),
                v.getSsn()
        );
    }
}
