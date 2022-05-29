package com.example.demo.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Product {
    @Id
    @GeneratedValue(generator = "UUID")
    private  UUID uuid;
    private  String name;
    private  String description;
    private  LocalDateTime createdAt;
    private  LocalDateTime modifiedAt;


}
