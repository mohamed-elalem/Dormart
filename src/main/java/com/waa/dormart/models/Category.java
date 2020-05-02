package com.waa.dormart.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Category {

    public static class CategoryBuilder implements ModelBuilder<Category> {
        private Category category;

        public CategoryBuilder() {
            this.category = new Category();
        }

        public CategoryBuilder withName(String name) {
            category.setName(name);
            return this;
        }

        public CategoryBuilder withDescription(String description) {
            category.setDescription(description);
            return this;
        }

        @Override
        public Category build() {
            return category;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{model.notBlank.error}")
    @Size(min = 3, max = 30, message = "{model.size.error}")
    private String name;

    @NotBlank(message = "{model.notBlank.error}")
    @Size(min = 3, max = 300, message = "{model.size.error}")
    private String description;

    @Valid
    @OneToMany(mappedBy = "category")
    private List<Product> products;

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public static CategoryBuilder create() {
        return new CategoryBuilder();
    }
}
