package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.ImageEntity;
import Geeks.languagecenterapp.Model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {

    List<ImageEntity> findImageByUserId(Integer id);

}
