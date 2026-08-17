package animalsworld.api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String residentName;

    private Integer streetNumber;

    private String predirectional;

    private String streetName;

    private String streetSuffix;

    private String postdirectional;

    private String secAddressIdentifier;

    private String city;

    private String state;

    private String zipCode;

    private String deliveryPoint;

    private String country;

    private String complement;

    public Address(AddressDTO dto) {
        this.residentName = dto.residentName();
        this.streetNumber = dto.streetNumber();
        this.predirectional = dto.predirectional();
        this.streetName = dto.streetName();
        this.streetSuffix = dto.streetSuffix();
        this.postdirectional = dto.postdirectional();
        this.secAddressIdentifier = dto.secAddressIdentifier();
        this.city = dto.city();
        this.state = dto.state();
        this.zipCode = dto.zipCode();
        this.deliveryPoint = dto.deliveryPoint();
        this.country = dto.country();
        this.complement = dto.complement();
    }

    public void updateAddress(AddressDTO dto) {
        if (dto.residentName() != null) {
            this.residentName = dto.residentName();
        }

        if (dto.streetNumber() != null) {
            this.streetNumber = dto.streetNumber();
        }

        if (dto.predirectional() != null) {
            this.predirectional = dto.predirectional();
        }

        if (dto.streetName() != null) {
            this.streetName = dto.streetName();
        }

        if (dto.streetSuffix() != null) {
            this.streetSuffix = dto.streetSuffix();
        }

        if (dto.postdirectional() != null) {
            this.postdirectional = dto.postdirectional();
        }

        if (dto.secAddressIdentifier() != null) {
            this.secAddressIdentifier = dto.secAddressIdentifier();
        }

        if (dto.city() != null) {
            this.city = dto.city();
        }

        if (dto.state() != null) {
            this.state = dto.state();
        }

        if (dto.zipCode() != null) {
            this.zipCode = dto.zipCode();
        }

        if (dto.deliveryPoint() != null) {
            this.deliveryPoint = dto.deliveryPoint();
        }

        if (dto.country() != null) {
            this.country = dto.country();
        }

        if (dto.complement() != null) {
            this.complement = dto.complement();
        }
    }
}
