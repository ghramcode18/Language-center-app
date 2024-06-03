package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.EnrollCourseEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollCourseRepository extends JpaRepository<EnrollCourseEntity,Integer> {
    List<EnrollCourseEntity> findByUser(UserEntity user);
}
