package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Model.UserQuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserQuizRepository extends JpaRepository<UserQuizEntity, Integer> {
    Optional<UserQuizEntity> findByUser(UserEntity user);

    Optional<UserQuizEntity> findByUserIdAndQuizId(int id, int id1);
}
