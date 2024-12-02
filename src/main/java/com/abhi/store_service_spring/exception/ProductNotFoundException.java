package com.abhi.store_service_spring.exception;

public class ProductNotFoundException extends ApiException {

    private final Long productId;
    public ProductNotFoundException(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
