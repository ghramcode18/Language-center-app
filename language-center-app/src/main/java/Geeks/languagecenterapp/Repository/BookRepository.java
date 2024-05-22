package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    Optional<BookEntity> findByUserIdAndPlacementTestId(int userId, int placementTestId);

    @Query("SELECT COUNT(b) FROM BookEntity b WHERE b.placementTest.id = :placementTestId")
    int countByPlacementTestId(@Param("placementTestId") int placementTestId);

    List<BookEntity> findByPlacementTestId(int placementTestId);

}
