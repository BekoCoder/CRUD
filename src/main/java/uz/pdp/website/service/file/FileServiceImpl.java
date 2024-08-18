package uz.pdp.website.service.file;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {


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

