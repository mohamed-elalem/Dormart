package com.waa.dormart.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Review {

    public static class ReviewBuilder implements ModelBuilder<Review> {
        private Review review;

        public ReviewBuilder() {
            review = new Review();
        }

        public ReviewBuilder withContent(String content) {
            review.setContent(content);
            return this;
        }

        public ReviewBuilder withReviewer(User reviewer) {
            review.setReviewer(reviewer);
            return this;
        }

        public ReviewBuilder withProduct(Product product) {
            review.setProduct(product);
            return this;
        }

        public ReviewBuilder withApproved(Boolean approved) {
            review.setApproved(approved);
            return this;
        }

        @Override
        public Review build() {
            return review;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6, max = 500, message = "{model.size.error}")
    private String content;

    @NotNull
    private Boolean approved;

    @ManyToOne
    private User reviewer;

    @ManyToOne
    private Product product;

    @CreationTimestamp
    private LocalDate createdAt;

    public Review() {
        approved = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public static ReviewBuilder create() {
        return new ReviewBuilder();
    }
}
