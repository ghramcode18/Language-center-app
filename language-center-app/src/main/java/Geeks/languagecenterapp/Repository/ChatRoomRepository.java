package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Integer> {
}
