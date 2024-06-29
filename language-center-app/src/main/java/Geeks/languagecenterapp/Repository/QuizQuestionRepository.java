package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.QuizQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestionEntity, Integer> {
    Optional<QuizQuestionEntity> findByQuizIdAndQuestionId(int quizId, int questionId);
    List<QuizQuestionEntity> findByQuizId(int quizId);
}
