package uz.pdp.website.service.user;

import org.springframework.core.io.Resource;
import uz.pdp.website.entity.UserEntity;

import java.io.File;
import java.io.IOException;

public interface FileService {
    Resource loadFileAsResource(String filePath) throws IOException;
}
