package animalsworld.api.domain.workData;

import jakarta.validation.constraints.NotNull;

public record WorkDataDTO(
   @NotNull
   Specialization specialization,
   @NotNull
   ContractType contractType,
   @NotNull
   WorkingShift workingShift,
   WorkingSituation workingSituation
) {
    public WorkDataDTO(WorkData wd) {
        this(
                wd.getSpecialization(),
                wd.getContractType(),
                wd.getWorkingShift(),
                wd.getWorkingSituation()
        );
    }
}
