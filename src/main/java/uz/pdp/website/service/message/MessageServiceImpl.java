package uz.pdp.website.service.message;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.website.entity.MessageEntity;
import uz.pdp.website.entity.UserEntity;
import uz.pdp.website.repository.MessageRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<MessageEntity> getAllMessage() {
        return  messageRepository.findAll();

    }

    @Override
    public MessageEntity create(String text, UUID uuid, String name) {
        MessageEntity map = modelMapper.map(text, MessageEntity.class);
        map.setMessage(text);
        map.setSenderId(uuid);
        map.setSenderName(name);
        return  messageRepository.save(map);
    }
}
