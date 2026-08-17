package animalsworld.api.domain.tour;

import animalsworld.api.domain.guide.Guide;
import animalsworld.api.domain.guide.GuideRepository;
import animalsworld.api.domain.visitor.Visitor;
import animalsworld.api.domain.visitor.VisitorRepository;
import animalsworld.api.infra.exception.BusinessRuleException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TourService {

    private static final Long NEW_TOUR = -1L;

    private final TourRepository tourR;
    private final GuideRepository guideR;
    private final VisitorRepository visitorR;

    public TourService(TourRepository tourR, GuideRepository guideR, VisitorRepository visitorR) {
        this.tourR = tourR;
        this.guideR = guideR;
        this.visitorR = visitorR;
    }

    public TourInfoDTO getTourInfoById(Long id) {
        Tour t = findTour(id);
        return new TourInfoDTO(t);
    }

    public Page<TourListingDTO> listToursPerPage(Pageable page) {
        return tourR.findAllByActiveTrue(page).map(TourListingDTO::new);
    }

    public Page<TourListingDTO> listToursByGuidePerPage(Long guideId, Pageable page) {
        return tourR.findAllByActiveTrueAndGuideId(guideId, page).map(TourListingDTO::new);
    }

    @Transactional
    public Tour scheduleTour(TourScheduleDTO dto) {
        Guide g = findGuide(dto.guideId());
        checkGuideAvailability(g.getId(), NEW_TOUR, dto.tourSchedule());

        Tour t = new Tour(g, dto.tourSchedule());
        tourR.save(t);
        return t;
    }

    @Transactional
    public void updateTour(TourUpdateDTO dto) {
        Tour t = findTour(dto.id());

        LocalDateTime schedule = dto.tourSchedule() != null ? dto.tourSchedule() : t.getTourSchedule();
        Long guideId = dto.guideId() != null ? dto.guideId() : t.getGuide().getId();

        checkGuideAvailability(guideId, t.getId(), schedule);

        if (dto.tourSchedule() != null) {
            for (Visitor v : t.getVisitors()) {
                checkVisitorAvailability(v.getId(), t.getId(), schedule);
            }
            t.reschedule(dto.tourSchedule());
        }

        if (dto.guideId() != null) {
            t.changeGuide(findGuide(dto.guideId()));
        }
    }

    @Transactional
    public void addVisitorToTour(Long tourId, Long visitorId) {
        Tour t = findTour(tourId);
        Visitor v = findVisitor(visitorId);

        checkVisitorAvailability(v.getId(), t.getId(), t.getTourSchedule());
        t.addVisitor(v);
    }

    @Transactional
    public void removeVisitorFromTour(Long tourId, Long visitorId) {
        Tour t = findTour(tourId);
        Visitor v = findVisitor(visitorId);
        t.removeVisitor(v);
    }

    @Transactional
    public void cancelTourById(Long id) {
        Tour t = findTour(id);
        t.cancelTour();
    }

    private Tour findTour(Long id) {
        Tour t = tourR.getTourById(id);
        if (t == null) {
            throw new EntityNotFoundException("Tour does not exist.");
        }
        return t;
    }

    private Guide findGuide(Long id) {
        Guide g = guideR.getGuideById(id);
        if (g == null) {
            throw new EntityNotFoundException("Guide does not exist.");
        }
        if (Boolean.FALSE.equals(g.getActive())) {
            throw new BusinessRuleException("This guide is inactive.");
        }
        return g;
    }

    private Visitor findVisitor(Long id) {
        Visitor v = visitorR.getVisitorById(id);
        if (v == null) {
            throw new EntityNotFoundException("Visitor does not exist.");
        }
        return v;
    }

    private void checkGuideAvailability(Long guideId, Long tourId, LocalDateTime schedule) {
        if (tourR.guideIsBusy(guideId, tourId, schedule.minus(Tour.DURATION), schedule.plus(Tour.DURATION))) {
            throw new BusinessRuleException("This guide already has a tour scheduled for this time.");
        }
    }

    private void checkVisitorAvailability(Long visitorId, Long tourId, LocalDateTime schedule) {
        if (tourR.visitorIsBusy(visitorId, tourId, schedule.minus(Tour.DURATION), schedule.plus(Tour.DURATION))) {
            throw new BusinessRuleException("This visitor already has a tour scheduled for this time.");
        }
    }
}
