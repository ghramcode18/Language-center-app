package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.CourseImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseImageRepository extends JpaRepository<CourseImageEntity,Integer> {
}
