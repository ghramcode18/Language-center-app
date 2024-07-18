package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,Integer> {
    Collection<Object> findByServiceId(int id);

    List<CourseEntity> findByOrderByStartDateDesc();

    List<CourseEntity> findByDiscountGreaterThan(int i);

    // Custom query to get top-rated courses
    @Query("SELECT c FROM CourseEntity c JOIN c.enrolledCourseList e GROUP BY c.id ORDER BY AVG(e.rate) DESC")
    List<CourseEntity> findTopRatedCourses();

    @Query("SELECT AVG(e.rate) FROM EnrollCourseEntity e WHERE e.course.id = :courseId")
    float findAverageRatingByCourseId(int courseId);
}
