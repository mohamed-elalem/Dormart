package com.waa.dormart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartProductDTO {
    private Long productId;

    @NotNull(message = "{model.notNull.error}")
    @Positive(message = "{model.positive.error}")
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
