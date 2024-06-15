package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.UserQuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuizRepository extends JpaRepository<UserQuizEntity, Integer> {
}
