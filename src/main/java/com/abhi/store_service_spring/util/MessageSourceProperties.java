package com.abhi.store_service_spring.util;

public enum MessageSourceProperties {
    PRODUCT_NOT_FOUND("product.not.found");

    private final String value;

    MessageSourceProperties(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}