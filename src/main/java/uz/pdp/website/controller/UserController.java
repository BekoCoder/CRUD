package uz.pdp.website.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.website.dto.request.UserRequestDto;
import uz.pdp.website.entity.UserEntity;
import uz.pdp.website.repository.UserRepository;
import uz.pdp.website.service.AuthService;
import uz.pdp.website.service.user.UserService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final UserRepository userRepository;

    @PostMapping("/updateInfo")
    public String userInfo(@RequestParam("id") UUID id,
                           @RequestParam(value = "address") String address,
                           @RequestParam(value = "direction") String direction,
                           @RequestParam(value = "password") String password,
                           Model model
    ) {
        userService.updateUserInfo(address, direction, password, id);
        UserEntity user = userService.getbyId(id);
        model.addAttribute("myInfo", user);
//        model.addAttribute("users", userService.getAllUsers());
        return "userInformation";

    }

    @GetMapping("/myInfo")
    public String getMyInfo(Authentication authentication, Model model) {
        UserEntity info = (UserEntity) authentication.getPrincipal();
        model.addAttribute("myInfo", info);
        return "userInformation";
    }

    @PostMapping("/getMyInfo")
    public String getInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            UserEntity userInfo = (UserEntity) authentication.getPrincipal();
            userService.getMyInfoWithWord(userInfo.getName(), userInfo.getUsername(), userInfo.getPassword(), userInfo.getAddress(), userInfo.getDirection(), userInfo.getUserRoles(), userInfo.getCourse());
            String wordFileName=userInfo.getName()+".docx";
            model.addAttribute("myInfo", userInfo);
            model.addAttribute("wordFileName", wordFileName);
            model.addAttribute("message", "File downloaded successfully");
            return "userInformation";

        }
        else {
            model.addAttribute("warning", "File failed to load");
            return "userInformation";
        }
    }

//    @GetMapping(path = "/download/{name}")
//    public ResponseEntity<Resource> download(@PathVariable("name") String name, Model model) throws IOException {
//        File file = new File( name);
//        Path path = Paths.get(file.getAbsolutePath());
//        ByteArrayResource resource = new ByteArrayResource
//                (Files.readAllBytes(path));
//            model.addAttribute("file", file);
//        return ResponseEntity.ok().headers(this.headers(name))
//                .contentLength(file.length())
//                .contentType(MediaType.parseMediaType
//                        ("application/octet-stream")).body((Resource) resource);
//    }
//    @GetMapping("/download/{username}")
//    public String downloadUserInfo(@PathVariable String username, Model model){
//
//    }





//    private HttpHeaders headers(String name) {
//
//        HttpHeaders header = new HttpHeaders();
//        header.add(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=" + name);
//        header.add("Cache-Control", "no-cache, no-store,"
//                + " must-revalidate");
//        header.add("Pragma", "no-cache");
//        header.add("Expires", "0");
//        return header;
//
//    }
}
