package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.MarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<MarkEntity, Integer> {
}
