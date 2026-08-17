package animalsworld.api.domain.workData;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkData {

    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Enumerated(EnumType.STRING)
    private WorkingShift workingShift;

    @Enumerated(EnumType.STRING)
    private WorkingSituation workingSituation;

    public WorkData(WorkDataDTO dto) {
        this.specialization = dto.specialization();
        this.contractType = dto.contractType();
        this.workingShift = dto.workingShift();
        this.workingSituation = WorkingSituation.WORKING;
    }

    public void updateWorkData(WorkDataDTO dto) {
        if (dto.specialization() != null) {
            this.specialization = dto.specialization();
        }

        if (dto.contractType() != null) {
            this.contractType = dto.contractType();
        }

        if (dto.workingShift() != null) {
            this.workingShift = dto.workingShift();
        }

        if (dto.workingSituation() != null) {
            this.workingSituation = dto.workingSituation();
        }
    }
}
