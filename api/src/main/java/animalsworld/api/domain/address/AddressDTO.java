package animalsworld.api.domain.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddressDTO(
        @NotBlank
        String residentName,

        @NotNull
        Integer streetNumber,

        String predirectional,

        @NotBlank
        String streetName,

        String streetSuffix,

        String postdirectional,

        String secAddressIdentifier,

        @NotBlank
        String city,

        @NotBlank
        String state,

        @NotBlank
        @Pattern(regexp = "^\\d{5}(?:-\\d{4})?$")
        String zipCode,

        String deliveryPoint,

        String country,

        String complement
) {
    public AddressDTO(Address a) {
        this(
                a.getResidentName(),
                a.getStreetNumber(),
                a.getPredirectional(),
                a.getStreetName(),
                a.getStreetSuffix(),
                a.getPostdirectional(),
                a.getSecAddressIdentifier(),
                a.getCity(),
                a.getState(),
                a.getZipCode(),
                a.getDeliveryPoint(),
                a.getCountry(),
                a.getComplement()
        );
    }
}
