package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.PlacementTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacementTestRepository extends JpaRepository<PlacementTestEntity,Integer> {
}
