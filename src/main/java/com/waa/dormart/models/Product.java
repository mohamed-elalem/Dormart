package com.waa.dormart.models;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
public class Product {

    public static class ProductBuilder implements ModelBuilder<Product> {
        private Product product;

        public ProductBuilder() {
            this.product = new Product();
        }

        public ProductBuilder withName(String name) {
            product.setName(name);
            return this;
        }

        public ProductBuilder withDescription(String description) {
            product.setDescription(description);
            return this;
        }

        public ProductBuilder withQuantity(Integer quantity) {
            product.setQuantity(quantity);
            return this;
        }

        public ProductBuilder withPrice(Double price) {
            product.setPrice(price);
            return this;
        }

        public ProductBuilder withSeller(User seller) {
            product.setSeller(seller);
            return this;
        }

        public ProductBuilder withCategory(Category category) {
            product.setCategory(category);
            return this;
        }

        @Override
        public Product build() {
            return product;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{model.notBlank.error}")
    @Size(min = 4, max = 50, message = "{model.size.error}")
    private String name;

    @NotBlank(message = "{model.notBlank.error}")
    @Size(min = 4, max = 600, message = "{model.size.error}")
    private String description;


    @Positive(message = "{model.positive.error}")
    @NotNull(message = "{model.notNull.error}")
    private Integer quantity;

    @Positive(message = "{model.positive.error}")
    @NotNull(message = "{model.notNull.error}")
    private Double price;

    @ManyToOne
    private User seller;

    @NotNull(message = "{model.notNull.error}")
    @ManyToOne
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static ProductBuilder create() {
        return new ProductBuilder();
    }
}
