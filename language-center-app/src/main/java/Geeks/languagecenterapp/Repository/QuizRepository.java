package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<QuizEntity, Integer> {
}
