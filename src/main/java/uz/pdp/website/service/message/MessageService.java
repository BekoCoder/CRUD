package uz.pdp.website.service.message;

import uz.pdp.website.entity.MessageEntity;
import uz.pdp.website.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    List<MessageEntity> getAllMessage();
    MessageEntity create(String text, UUID userId);

}
