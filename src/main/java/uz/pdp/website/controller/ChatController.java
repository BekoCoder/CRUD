package uz.pdp.website.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.website.entity.ChatMessage;
import uz.pdp.website.entity.UserEntity;
import uz.pdp.website.service.chat.ChatService;
import uz.pdp.website.service.user.UserService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/conversation")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    @GetMapping("/chat")
    public String chat(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            UserEntity currentUser = (UserEntity) authentication.getPrincipal();
            List<UserEntity> allUsersExceptCurrent = userService.getAllUsersExceptCurrent(currentUser);
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("allUsers", allUsersExceptCurrent);
            model.addAttribute("chatMessage", new ChatMessage());
            return "feedback";
        }
        return "feedback";
    }
    @PostMapping("/sendChatMessage")
    public String sendChatMessage(@ModelAttribute("chatMessage") ChatMessage chatMessage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            UserEntity currentUser = (UserEntity) authentication.getPrincipal();
            chatMessage.setSender(currentUser);
            chatService.saveChatMessage(chatMessage);
        }
        return "feedback";
    }

    @GetMapping("/loadChatMessages")
    public String loadChatMessages(UUID recipientId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            UserEntity currentUser = (UserEntity) authentication.getPrincipal();
            UserEntity recipient = userService.getbyId(recipientId);
            List<ChatMessage> chatMessages = chatService.getChatMessages(currentUser, recipient);
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("recipient", recipient);
            model.addAttribute("chatMessages", chatMessages);
        }
        return "feedback";
    }
}
