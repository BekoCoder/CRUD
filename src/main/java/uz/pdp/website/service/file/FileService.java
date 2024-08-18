package uz.pdp.website.service.file;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface FileService {
    Resource loadFileAsResource(String filePath) throws IOException;
}
