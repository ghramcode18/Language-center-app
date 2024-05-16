package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.CourseDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDayRepository extends JpaRepository<CourseDayEntity,Integer> {
}
