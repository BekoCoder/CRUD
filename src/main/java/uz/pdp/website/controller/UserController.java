package uz.pdp.website.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
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
import uz.pdp.website.service.user.FileServiceImpl;
import uz.pdp.website.service.user.UserService;
import org.springframework.core.io.Resource;
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
    private final FileServiceImpl fileService;


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
            userService.getMyInfoWithWord("user_info",userInfo);
            String wordFileName="user_info.docx";
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


    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletResponse response, Model model) throws IOException {
        // Fayl yo'lini olish, masalan, FileServiceImpl orqali
        String filePath = "src/main/resources/static/" + fileName; // Sizning kerakli yo'lnga moslashtiring
        Resource resource = fileService.loadFileAsResource(filePath);
        // Faylni ko'chirib olish uchun Response
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
