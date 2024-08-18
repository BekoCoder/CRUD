package uz.pdp.website.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.website.dto.request.AuthDto;
import uz.pdp.website.dto.request.UserRequestDto;
import uz.pdp.website.entity.UserEntity;
import uz.pdp.website.service.user.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @GetMapping("/login")
    public String loginPage() {
        log.info(
                "User Data: {}",
                SecurityContextHolder.getContext().getAuthentication()!= null ? SecurityContextHolder.getContext().getAuthentication().getName() : "null"
        );
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AuthDto auth, Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.isAuthenticated() &&  !authentication.getPrincipal().equals("anonymousUser")){
            UserEntity user = userService.login(auth);
            model.addAttribute("user", user);
            model.addAttribute("trans", "Succesfully enter");
            return "login";
        }
        else {
            model.addAttribute("sms", "User Not Found");
            return "login";
        }




    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserRequestDto requestDto) {
        userService.create(requestDto);
        return "index";

    }


}
