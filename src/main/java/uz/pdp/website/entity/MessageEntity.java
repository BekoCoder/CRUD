package uz.pdp.website.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name="messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @JoinColumn(name = "sender_id")
    private UUID senderId;

    private String senderName;


    private String message;



}
