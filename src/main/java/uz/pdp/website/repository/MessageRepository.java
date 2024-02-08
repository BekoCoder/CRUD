package uz.pdp.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.website.entity.MessageEntity;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    Optional<MessageEntity> findByMessage(String message);

}
