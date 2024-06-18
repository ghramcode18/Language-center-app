package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.UserQuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuizRepository extends JpaRepository<UserQuizEntity, Integer> {
}
