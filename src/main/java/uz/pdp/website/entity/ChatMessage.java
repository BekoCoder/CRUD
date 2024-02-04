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
public class ChatMessage {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;


    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserEntity recipient;

    private String message;



}
