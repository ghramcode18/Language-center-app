package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    Optional<BookEntity> findByUserIdAndPlacementTestId(int userId, int placementTestId);
}
