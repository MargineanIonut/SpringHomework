package com.example.demo.model;

import lombok.*;

import javax.persistence.Entity;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportTransactionType {
    Type type;
    Double ammout;
}
