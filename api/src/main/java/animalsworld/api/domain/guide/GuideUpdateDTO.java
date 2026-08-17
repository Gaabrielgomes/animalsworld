package animalsworld.api.domain.guide;

import animalsworld.api.domain.address.AddressDTO;
import animalsworld.api.domain.workData.WorkDataDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record GuideUpdateDTO(
   @NotNull
   Long id,
   String name,
   @Email
   String email,
   String phone,
   @Valid
   AddressDTO addressDTO,
   @Valid
   WorkDataDTO workDataDTO
) {
    public GuideUpdateDTO(Guide g) {
        this(
                g.getId(),
                g.getName(),
                g.getEmail(),
                g.getPhone(),
                new AddressDTO(g.getAddress()),
                new WorkDataDTO(g.getWorkData())
        );
    }
}
