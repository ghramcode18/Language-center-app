package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer> {
}
