package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.PlacementTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PlacementTestRepository extends JpaRepository<PlacementTestEntity,Integer> {
    List<PlacementTestEntity> findByMaxNum(int maxNum);

    List<PlacementTestEntity> findByLanguage(String language);
}
