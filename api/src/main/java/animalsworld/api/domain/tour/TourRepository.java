package animalsworld.api.domain.tour;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TourRepository extends JpaRepository<Tour, Long> {
    Tour getTourById(Long id);

    Page<Tour> findAllByActiveTrue(Pageable page);

    Page<Tour> findAllByActiveTrueAndGuideId(Long guideId, Pageable page);

    @Query("""
           SELECT COUNT(t) > 0 FROM Tour t
           WHERE t.guide.id = :guideId
           AND t.active = true
           AND t.id <> :tourId
           AND t.tourSchedule > :start
           AND t.tourSchedule < :end
           """)
    boolean guideIsBusy(@Param("guideId") Long guideId,
                        @Param("tourId") Long tourId,
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);

    @Query("""
           SELECT COUNT(t) > 0 FROM Tour t
           JOIN t.visitors v
           WHERE v.id = :visitorId
           AND t.active = true
           AND t.id <> :tourId
           AND t.tourSchedule > :start
           AND t.tourSchedule < :end
           """)
    boolean visitorIsBusy(@Param("visitorId") Long visitorId,
                          @Param("tourId") Long tourId,
                          @Param("start") LocalDateTime start,
                          @Param("end") LocalDateTime end);
}
