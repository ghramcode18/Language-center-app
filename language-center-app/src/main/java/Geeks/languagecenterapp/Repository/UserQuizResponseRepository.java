package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.UserQuizResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuizResponseRepository extends JpaRepository<UserQuizResponseEntity, Integer> {
}
