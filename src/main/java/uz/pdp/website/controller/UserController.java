package uz.pdp.website.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.website.entity.ImageEntity;
import uz.pdp.website.entity.UserEntity;
import uz.pdp.website.repository.UserRepository;
import uz.pdp.website.service.file.FileServiceImpl;
import uz.pdp.website.service.image.ImageService;
import uz.pdp.website.service.user.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final FileServiceImpl fileService;
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    @PostMapping("/updateInfo")
    public String userInfo(@RequestParam("id") UUID id,
                           @RequestParam(value = "address") String address,
                           @RequestParam(value = "direction") String direction,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "jshshir") String jshshir,
                           @RequestParam(value = "placeOfBirth") String placeofBirth,
                           @RequestParam(value = "dateOfBirth") String dateOfBirth,
                           @RequestParam(value = "nationality") String nationality,
                           Model model
    ) {
        userService.updateUserInfo(address, direction, password, id, jshshir, placeofBirth, dateOfBirth, nationality);
        UserEntity user = userService.getbyId(id);
        model.addAttribute("myInfo", user);
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
            UUID id = ((UserEntity) authentication.getPrincipal()).getId();
            UserEntity userInfo = userService.getbyId(id);
            userService.getMyInfoWithWord("user_info", userInfo);
            String wordFileName = "user_info.docx";
            model.addAttribute("myInfo", userInfo);
            model.addAttribute("wordFileName", wordFileName);
            model.addAttribute("message", "File downloaded successfully");
            return "userInformation";

        } else {
            model.addAttribute("warning", "File failed to load");
            return "userInformation";
        }
    }

    @GetMapping("/getMyImage")
    public String getMyImage(Model model, Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        List<ImageEntity> allImages = imageService.getAllImage();
        List<ImageEntity> userImages = new ArrayList<>();
        for (int i = 0; i < allImages.size(); i++) {
            if (user.getId().equals(allImages.get(i).getUserId()))
                userImages.add(allImages.get(i));
        }
        model.addAttribute("image", userImages);
        model.addAttribute("user", user);
        return "pictures";
    }

    @GetMapping("/image")
    public void image(@Param(value = "id") Long id, HttpServletResponse response, Optional<ImageEntity> image) throws IOException {
        image = imageService.findImageById(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/pdf");
        response.getOutputStream().write(image.get().getContent());
        response.getOutputStream().close();
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, Model model, Authentication authentication, ImageEntity imageEntity) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload.");
            return "pictures";
        }

        try {
            String filename = file.getOriginalFilename();
            imageEntity.setProfilePicture(filename);
            imageEntity.setContent(file.getBytes());
            imageEntity.setSize(file.getSize());
            imageEntity.setUserId(user.getId());
            imageService.create(imageEntity);

            model.addAttribute("success", "File uploaded successfully!");
//            model.addAttribute("list", imageService.getAllImage());
//            model.addAttribute("user", user);
        } catch (IOException e) {
            model.addAttribute("error", "Error uploading the file. Please try again.");
        }
        List<ImageEntity> allImages = imageService.getAllImage();
        List<ImageEntity> userImages = new ArrayList<>();
        for (int i = 0; i < allImages.size(); i++) {
            if (user.getId().equals(allImages.get(i).getUserId()))
                userImages.add(allImages.get(i));
        }
        model.addAttribute("image", userImages);
        model.addAttribute("user", user);
        return "pictures";

    }

    @GetMapping("/downloadFile")
    public void downloadFile(@Param("id") Long id, Model model, HttpServletResponse response) throws IOException {
        Optional<ImageEntity> imageById = imageService.findImageById(id);
        if (imageById.isPresent()) {
            ImageEntity image = imageById.get();
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename = " + image.getProfilePicture();
            response.setHeader(headerKey, headerValue);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(image.getContent());
            outputStream.close();
        }
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletResponse response, Model model) throws IOException {
        // Fayl yo'lini olish, masalan, FileServiceImpl orqali
        String filePath = "C:\\Users\\user\\Desktop\\" + fileName; // Fayl joylashgan manzil
        Resource resource = fileService.loadFileAsResource(filePath);
        // Faylni ko'chirib olish uchun Response
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
