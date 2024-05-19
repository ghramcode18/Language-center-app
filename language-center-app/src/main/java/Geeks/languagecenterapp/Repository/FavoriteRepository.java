package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity,Integer> {
}
