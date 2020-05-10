package com.waa.dormart.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingOrder {

    public static class OrderBuilder implements ModelBuilder<ShoppingOrder> {

        private ShoppingOrder shoppingOrder;

        public OrderBuilder() {
            shoppingOrder = new ShoppingOrder();
        }

        public OrderBuilder withItems(List<Item> items) {
            shoppingOrder.setItems(items);
            return this;
        }

        public OrderBuilder withOrderStatus(OrderStatus orderStatus) {
            shoppingOrder.setOrderStatus(orderStatus);
            return this;
        }

        public OrderBuilder withBuyer(User buyer) {
            shoppingOrder.setBuyer(buyer);
            return this;
        }

        public OrderBuilder withSeller(User seller) {
            shoppingOrder.setSeller(seller);
            return this;
        }

        public OrderBuilder withPrice(Double price) {
            shoppingOrder.setPrice(price);
            return this;
        }

        public OrderBuilder withCardNumber(String cardNumber) {
            shoppingOrder.setCardNumber(cardNumber);
            return this;
        }

        public OrderBuilder withCvv(String cvv) {
            shoppingOrder.setCvv(cvv);
            return this;
        }

        public OrderBuilder withCity(String city) {
            shoppingOrder.setCity(city);
            return this;
        }

        public OrderBuilder withState(String state) {
            shoppingOrder.setState(state);
            return this;
        }

        public OrderBuilder withZipcode(String zipcode) {
            shoppingOrder.setZipcode(zipcode);
            return this;
        }

        @Override
        public ShoppingOrder build() {
            return shoppingOrder;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "shoppingOrder")
    @Cascade(CascadeType.ALL)
    private List<Item> items;

    @ManyToOne
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    private Double price;

    @UpdateTimestamp
    private LocalDate updatedAt;

    public ShoppingOrder() {
        items = new ArrayList<>();
    }

    @NotEmpty(message = "{model.notEmpty.error}")
    private String cardNumber;

    @Pattern(regexp = "^[0-9]{3,4}$", message = "{model.regex.error}")
    private String cvv;

    @NotEmpty(message = "{model.notEmpty.error}")
    private String city;

    @NotEmpty(message = "{model.notEmpty.error}")
    private String state;

    @NotEmpty(message = "{model.notEmpty.error}")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "{model.regex.error}")
    private String zipcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static OrderBuilder create() {
        return new OrderBuilder();
    }
}
