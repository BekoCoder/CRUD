package uz.pdp.website.service.image;

import uz.pdp.website.entity.ImageEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageService {
    ImageEntity create(ImageEntity image);
    List<ImageEntity> getAllImage();

    Optional<ImageEntity> findImageById(long id);


}
