package uz.pdp.website.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.website.entity.UserEntity;
import uz.pdp.website.service.message.MessageService;

@Controller
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/getAllMessage")
    public String getAllMessage(Model model) {
        model.addAttribute("allMessage", messageService.getAllMessage());
        return "problems";
    }

    @PostMapping("/saveMessage")
    public String saveMessage(@RequestParam(name = "text") String text, Model model, Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        messageService.create(text, user.getId(), user.getName());
        return "feedback";
    }


}
