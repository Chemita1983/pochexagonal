package com.orange.shoppingcart.signature.domain.model;

import java.util.Arrays;

public enum Segment {

    RESIDENCIAL,
    AUTONOMO,
    EMPRESA,
    TODOS;


    public static Boolean isNotValidValue(final String segment) {
        if (Arrays.stream(Segment.values()).noneMatch(segmentEnumValue ->
                segmentEnumValue.name().equalsIgnoreCase(segment))) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
