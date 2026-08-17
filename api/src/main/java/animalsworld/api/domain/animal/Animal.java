package animalsworld.api.domain.animal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "animals")
@Entity(name = "Animal")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthDate;

    private LocalDate deathDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mother_id")
    private Animal mother;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "father_id")
    private Animal father;

    @Enumerated(EnumType.STRING)
    private AnimalGender gender;

    private String race;

    private String specie;

    @Enumerated(EnumType.STRING)
    private AnimalClass animalClass;

    private Boolean extinctionWarning;

    private Boolean active = Boolean.TRUE;

    public Animal(AnimalRegisterDTO dto, Animal mother, Animal father) {
        this.name = dto.name();
        this.birthDate = dto.birthDate();
        this.mother = mother;
        this.father = father;
        this.gender = dto.gender();
        this.race = dto.race();
        this.specie = dto.specie();
        this.animalClass = dto.animalClass();
        this.extinctionWarning = dto.extinctionWarning();
        this.active = Boolean.TRUE;
    }

    public void updateAnimal(AnimalUpdateDTO dto) {
        if (dto.name() != null) {
            this.name = dto.name();
        }

        if (dto.deathDate() != null) {
            this.deathDate = dto.deathDate();
        }

        if (dto.race() != null) {
            this.race = dto.race();
        }

        if (dto.specie() != null) {
            this.specie = dto.specie();
        }

        if (dto.animalClass() != null) {
            this.animalClass = dto.animalClass();
        }

        if (dto.extinctionWarning() != null) {
            this.extinctionWarning = dto.extinctionWarning();
        }
    }

    public void inactiveAnimal() { this.active = Boolean.FALSE; }
}
