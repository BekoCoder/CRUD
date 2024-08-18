package uz.pdp.website.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.website.entity.ImageEntity;
import uz.pdp.website.repository.ImageRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public ImageEntity create(ImageEntity image) {
        return imageRepository.save(image);
    }

    @Override
    public List<ImageEntity> getAllImage() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<ImageEntity> findImageById(long id) {

        return imageRepository.findById(id);
    }
}
