package uz.pdp.website.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.website.entity.ChatMessage;
import uz.pdp.website.entity.UserEntity;
import uz.pdp.website.repository.ChatMessageRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private ChatMessageRepository chatMessageRepository;
    @Override
    public List<ChatMessage> getChatMessages(UserEntity sender, UserEntity recipient) {
        return chatMessageRepository.findBySenderAndRecipientOrSenderAndRecipient(sender, recipient,
                recipient, sender );

    }

    @Override
    public void saveChatMessage(ChatMessage chatMessage) {
            chatMessageRepository.save(chatMessage);
    }
}
