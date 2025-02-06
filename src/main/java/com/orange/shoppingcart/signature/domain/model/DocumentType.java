package com.orange.shoppingcart.signature.domain.model;

import java.util.Arrays;

public enum DocumentType {

    NIF("NIF"),
    CIF("CIF"),
    RESIDENCE_CARD("TARJETA DE RESIDENCIA"),
    PASSPORT("PASAPORTE"),
    OTHER("OTHERS"),
    TODOS("TODOS");


    private final String document;

    DocumentType(final String document) {
        this.document = document;
    }

    public String getDocument() {
        return document;
    }

    public static Boolean isNotValidValue(final String document) {
        if (Arrays.stream(DocumentType.values()).noneMatch(documentType -> documentType.getDocument().equalsIgnoreCase(document))) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
