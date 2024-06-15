package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.QuizQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestionEntity, Integer> {
}
