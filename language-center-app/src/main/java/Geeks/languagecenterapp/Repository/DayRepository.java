package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.DayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<DayEntity, Integer> {
}
