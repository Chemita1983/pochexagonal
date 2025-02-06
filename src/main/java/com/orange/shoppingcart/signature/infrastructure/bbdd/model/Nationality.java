package com.orange.shoppingcart.signature.infrastructure.bbdd.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nationalities")
@Setter
@Getter
public class Nationality {

    @Id
    @Column(name = "nationality")
    private String nationality;
}
