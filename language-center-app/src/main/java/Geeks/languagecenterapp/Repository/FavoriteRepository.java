package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.CourseEntity;
import Geeks.languagecenterapp.Model.FavoriteEntity;
import Geeks.languagecenterapp.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity,Integer> {
    FavoriteEntity findByUserAndCourse(UserEntity user, CourseEntity courseEntity);

    List<FavoriteEntity> findByUser(UserEntity user);
}
