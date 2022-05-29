package com.example.demo.model;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;


@Entity
@Data
public class Role {
    @Id
    private Integer id;

    private String name;


}
