package uz.pdp.website.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String profilePicture;
    private long size;
    private byte[] content;
    @JoinColumn(name = "user_id")
    private UUID userId;
}
