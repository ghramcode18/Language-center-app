package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,Integer> {
    Collection<Object> findByServiceId(int id);
}
