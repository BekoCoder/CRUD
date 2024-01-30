package uz.pdp.website.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.website.dto.request.UserRequestDto;
import uz.pdp.website.entity.UserEntity;
import uz.pdp.website.repository.UserRepository;
import uz.pdp.website.service.AuthService;
import uz.pdp.website.service.user.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private  final UserRepository userRepository;

    @PostMapping("/Info")
    public String userInfo(@RequestParam("id")UUID id,
                           @RequestParam(value = "address") String address,
                           @RequestParam(value = "direction") String direction,
                           @RequestParam(value = "course") int course,
                           Model model
                           ){
        userService.updateUserInfo(address, direction, course, id);
        UserEntity user = userService.getbyId(id);
        model.addAttribute("users", user);
        model.addAttribute("users", userService.getAllUsers());
        return "userInformation";

    }

    @GetMapping("/myInfo")
    public String getMyInfo(Authentication authentication, Model model){
       UserEntity info=(UserEntity) authentication.getPrincipal();
       model.addAttribute("myInfo", info);
       return "userInformation";
    }




}
