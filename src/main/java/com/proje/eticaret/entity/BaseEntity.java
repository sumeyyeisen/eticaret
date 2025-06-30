package com.proje.eticaret.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
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
