package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.HomeWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeWorkRepository extends JpaRepository<HomeWorkEntity, Integer> {

    List<HomeWorkEntity> getByCourseId(int id);

}
