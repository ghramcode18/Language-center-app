package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.CourseImageEntity;
import Geeks.languagecenterapp.Model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseImageRepository extends JpaRepository<CourseImageEntity,Integer> {
    CourseImageEntity findByCourseId(int id);

    List<CourseImageEntity> findByPostId(int id);
}
