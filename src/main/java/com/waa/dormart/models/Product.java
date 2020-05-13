package com.waa.dormart.models;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product implements Serializable {

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


    @DecimalMin(value =  "0", message = "{model.decimalMin.error}")
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

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    private String image;

    public Product() {
        reviews = new ArrayList<>();

    }

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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public static ProductBuilder create() {
        return new ProductBuilder();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
