package uz.pdp.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/home")
    public String hs() {
        return "index";
    }

    @GetMapping("/admin")
    public String menu() {
        return "admin";
    }
    @GetMapping("/example")
    public String ex(){
        return "example";
    }

//    @GetMapping("/UserInformation")
//    public String info(){
//        return "update_user";
//    }


    @GetMapping("/edit_user")
    public String update(){
        return "edit_user";
    }





}
