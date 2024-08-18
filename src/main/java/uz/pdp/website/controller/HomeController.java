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

    @GetMapping("/pictures")
    public String menu() {
        return "pictures";
    }

    @GetMapping("/example")
    public String ex() {
        return "example";
    }

    @GetMapping("/edit_user")
    public String edit() {
        return "edit_user";
    }

    @GetMapping("/userInformation")
    public String userInfo() {
        return "/userInformation";
    }

    @GetMapping("/feedback")
    public String feedback() {
        return "feedback";
    }

    @GetMapping("/problems")
    public String problems() {
        return "/problems";
    }
}
