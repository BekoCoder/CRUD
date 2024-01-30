package uz.pdp.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.website.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);

 }
