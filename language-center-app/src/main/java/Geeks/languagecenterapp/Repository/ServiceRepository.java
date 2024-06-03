package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity,Integer> {
    @Query("SELECT DISTINCT s FROM ServiceEntity s LEFT JOIN FETCH s.courses")
    List<ServiceEntity> findAllWithCourses();
}
