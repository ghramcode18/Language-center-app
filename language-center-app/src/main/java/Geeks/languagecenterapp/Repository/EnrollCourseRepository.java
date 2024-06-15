package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.EnrollCourseEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollCourseRepository extends JpaRepository<EnrollCourseEntity,Integer> {
    @Query("SELECT e FROM EnrollCourseEntity e WHERE e.course.id = :courseId")
    List<EnrollCourseEntity> findByCourseId(int courseId);
    List<EnrollCourseEntity> findByUser(UserEntity user);

    Optional<EnrollCourseEntity> findByUserIdAndCourseId(int id, int id1);
}
