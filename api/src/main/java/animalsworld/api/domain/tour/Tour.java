package animalsworld.api.domain.tour;

import animalsworld.api.domain.guide.Guide;
import animalsworld.api.domain.visitor.Visitor;
import animalsworld.api.infra.exception.BusinessRuleException;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tours")
@Entity(name = "Tour")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Tour {

    public static final int MAX_VISITORS = 10;
    public static final Duration DURATION = Duration.ofHours(1);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private Guide guide;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tour_visitors",
            joinColumns = @JoinColumn(name = "tour_id"),
            inverseJoinColumns = @JoinColumn(name = "visitor_id")
    )
    private List<Visitor> visitors = new ArrayList<>();

    private LocalDateTime tourSchedule;

    private Boolean active = Boolean.TRUE;

    public Tour(Guide guide, LocalDateTime tourSchedule) {
        this.guide = guide;
        this.tourSchedule = tourSchedule;
        this.visitors = new ArrayList<>();
        this.active = Boolean.TRUE;
    }

    public LocalDateTime getTourEnd() { return this.tourSchedule.plus(DURATION); }

    public void addVisitor(Visitor v) {
        if (Boolean.FALSE.equals(this.active)) {
            throw new BusinessRuleException("This tour was canceled.");
        }

        if (this.tourSchedule.isBefore(LocalDateTime.now())) {
            throw new BusinessRuleException("This tour has already started.");
        }

        if (Boolean.FALSE.equals(v.getActive())) {
            throw new BusinessRuleException("This visitor is inactive.");
        }

        if (this.visitors.contains(v)) {
            throw new BusinessRuleException("This visitor is already in this tour group.");
        }

        if (this.visitors.size() >= MAX_VISITORS) {
            throw new BusinessRuleException("This tour group is already full. A tour holds up to " + MAX_VISITORS + " visitors.");
        }

        this.visitors.add(v);
    }

    public void removeVisitor(Visitor v) {
        if (!this.visitors.remove(v)) {
            throw new BusinessRuleException("This visitor is not in this tour group.");
        }
    }

    public void changeGuide(Guide g) {
        if (Boolean.FALSE.equals(g.getActive())) {
            throw new BusinessRuleException("This guide is inactive.");
        }
        this.guide = g;
    }

    public void reschedule(LocalDateTime newSchedule) {
        if (Boolean.FALSE.equals(this.active)) {
            throw new BusinessRuleException("This tour was canceled.");
        }
        this.tourSchedule = newSchedule;
    }

    public void cancelTour() {
        if (this.tourSchedule.isBefore(LocalDateTime.now())) {
            throw new BusinessRuleException("A tour that has already started cannot be canceled.");
        }
        this.active = Boolean.FALSE;
    }
}
