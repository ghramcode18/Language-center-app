package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.HomeWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeWorkRepository extends JpaRepository<HomeWorkEntity,Integer> {
}
