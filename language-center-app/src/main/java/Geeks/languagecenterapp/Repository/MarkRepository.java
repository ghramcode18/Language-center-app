package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.MarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<MarkEntity, Integer> {

    @Query(value = "SELECT m.file FROM Mark m WHERE m.course_id = :courseId", nativeQuery = true)
    Optional<String> findFilesByCourseId(@Param("courseId") Integer courseId);

}
