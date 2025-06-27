package com.proje.eticaret.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;

    @PrePersist
    protected void onCreate(){
        this.created = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updated = LocalDateTime.now();
    }

}
