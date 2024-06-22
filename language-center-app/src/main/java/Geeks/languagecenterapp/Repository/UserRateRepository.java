package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.UserEntity;
import Geeks.languagecenterapp.Model.UserRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRateRepository extends JpaRepository<UserRateEntity, Integer> {
    Optional<UserRateEntity> findByUser(UserEntity userEntity);
}
