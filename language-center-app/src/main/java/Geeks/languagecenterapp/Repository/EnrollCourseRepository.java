package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.EnrollCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollCourseRepository extends JpaRepository<EnrollCourseEntity,Integer> {
}
