package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.UserRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRateRepository extends JpaRepository<UserRateEntity, Integer> {
}
