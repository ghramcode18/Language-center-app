package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.CourseDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseDayRepository extends JpaRepository<CourseDayEntity,Integer> {
    Optional<CourseDayEntity> findByCourseIdAndDayId(int courseId, int dayId);
}
