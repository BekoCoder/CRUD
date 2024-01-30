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

//    @GetMapping("/update/{id}")
//    public String edit(@PathVariable(value = "id") UUID id, Model model){
//        UserEntity user = userService.getbyId(id);
//        model.addAttribute("users", user);
//        return "getAll";
//    }

    @PostMapping("/update")
    public String update(@RequestParam("id") UUID id,
                         @RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password, Model model )
    {
        userService.update(username, password, id);
        UserEntity user = userService.getbyId(id);
            model.addAttribute("users", user);
            model.addAttribute("users", userService.getAllUsers());
            return "getAll";

    }

}
