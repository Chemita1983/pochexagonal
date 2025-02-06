package com.orange.shoppingcart.signature.infrastructure.bbdd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SIGNATURE_CRITERIA")
@Setter
@Getter
public class SignatureEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "documenttype")
    private String documentType;

    @Column(name = "segment")
    private String segment;

    @Column(name = "commercialact")
    private String commercialAct;
}
