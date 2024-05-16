package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
}
