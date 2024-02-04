package uz.pdp.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.website.entity.ChatMessage;
import uz.pdp.website.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage> findBySenderAndRecipientOrSenderAndRecipient(
            UserEntity sender1, UserEntity recipient1, UserEntity sender2, UserEntity recipient2
    );

}
