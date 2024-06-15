package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {
}
