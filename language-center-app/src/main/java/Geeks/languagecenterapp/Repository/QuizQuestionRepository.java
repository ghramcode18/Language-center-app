package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.QuizQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestionEntity, Integer> {
}
