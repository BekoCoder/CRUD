package uz.pdp.website.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.website.dto.request.UserRequestDto;
import uz.pdp.website.entity.UserEntity;
import uz.pdp.website.service.user.UserService;

import java.util.UUID;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ModelMapper modelMapper;


    @GetMapping("/allUser")
    public String getAllUser(Model model){
        model.addAttribute("users", userService.getAllUsers());
       return "getAll";
    }



    @GetMapping("/delete")
    public String deletebyId(@RequestParam(value = "id")  UUID id, Model model){
        userService.deletebyId(id);
        model.addAttribute("users", userService.getAllUsers());
        return "getAll";
    }

    @GetMapping("/users/edit/{id}")
    public String edit(@PathVariable UUID id, Model model){
        model.addAttribute("users", userService.getbyId(id));
        return "edit_user";


    }

    @PostMapping("/users/{id}")
    public String update(@PathVariable UUID id, @ModelAttribute UserRequestDto requestDto, Model model) {
        UserEntity user = userService.getbyId(id);
        user.setId(id);
        user.setName(requestDto.getName());
        user.setUsername(requestDto.getUsername());
        UserRequestDto userDto = modelMapper.map(user, UserRequestDto.class);
        userService.update(userDto);
        return "getAll";

    }

}
