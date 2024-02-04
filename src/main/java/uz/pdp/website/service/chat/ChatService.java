package uz.pdp.website.service.chat;

import uz.pdp.website.entity.ChatMessage;
import uz.pdp.website.entity.UserEntity;

import java.util.List;

public interface ChatService {
    List<ChatMessage> getChatMessages(UserEntity sender, UserEntity recipient);

    void saveChatMessage(ChatMessage chatMessage);
}
