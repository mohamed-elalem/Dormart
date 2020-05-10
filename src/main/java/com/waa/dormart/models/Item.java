package com.waa.dormart.models;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
public class Item {

    public static class ItemBuilder implements ModelBuilder<Item> {
        private Item item;

        public ItemBuilder() {
            item = new Item();
        }

        public ItemBuilder withProduct(Product product) {
            item.setProduct(product);
            return this;
        }

        public ItemBuilder withQuantity(Integer quantity) {
            item.setQuantity(quantity);
            return this;
        }

        public ItemBuilder withShoppingOrder(ShoppingOrder shoppingOrder) {
            item.setShoppingOrder(shoppingOrder);
            return this;
        }

        @Override
        public Item build() {
            return item;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    @Positive(message = "model.positive.error")
    private Integer quantity;

    @ManyToOne
    private ShoppingOrder shoppingOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ShoppingOrder getShoppingOrder() {
        return shoppingOrder;
    }

    public void setShoppingOrder(ShoppingOrder shoppingOrder) {
        this.shoppingOrder = shoppingOrder;
    }

    public static ItemBuilder create() {
        return new ItemBuilder();
    }
}
