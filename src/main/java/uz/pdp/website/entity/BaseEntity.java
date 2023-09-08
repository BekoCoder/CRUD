package uz.pdp.website.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Setter
@Getter
public abstract class  BaseEntity {

    @Id
    @GeneratedValue
    protected UUID id;

     @CreationTimestamp
    protected LocalDateTime createDate;

     @UpdateTimestamp
     protected LocalDateTime updateDate;


}
