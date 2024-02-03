package uz.pdp.website.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import uz.pdp.website.entity.UserEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{


    @Override
    public Resource loadFileAsResource(String filePath) throws IOException {
        try {
            // Faylni olish uchun Resource yaratish
            Path fileStorageLocation = Paths.get(filePath).toAbsolutePath().normalize();
            Resource resource = new UrlResource(fileStorageLocation.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new IOException("Fayl topilmadi: " + filePath);
            }
        } catch (MalformedURLException ex) {
            throw new IOException("Fayl topilmadi: " + filePath, ex);
        }
    }


    }

