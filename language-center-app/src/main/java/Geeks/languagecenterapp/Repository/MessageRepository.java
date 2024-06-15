package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
}
