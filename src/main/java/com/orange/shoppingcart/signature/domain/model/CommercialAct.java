package com.orange.shoppingcart.signature.domain.model;

import java.util.Arrays;
import java.util.List;

public enum CommercialAct {

    ALTA("alta"),
    RENOVE("renove"),
    CAMBIO_TARIFA("cambiotarifa"),
    MIGRACION("migracion"),
    PORTABILIDAD("portabilidad"),
    MODIFICACION("modificacion"),
    TODOS("TODOS");

    private final String commercialAct;

    CommercialAct(final String commercialAct) {
        this.commercialAct = commercialAct;
    }

    public String getCommercialAct() {
        return commercialAct;
    }

    public static Boolean isNotValidValue(final List<String> commercialAct) {
        if (Arrays.stream(CommercialAct.values()).noneMatch(commercialActEnumValue ->
                commercialAct.contains(commercialActEnumValue.getCommercialAct()))) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }

}
