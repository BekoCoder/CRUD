package uz.pdp.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.website.entity.ImageEntity;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
