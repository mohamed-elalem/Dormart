package com.waa.dormart.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class OrderStatus {

    public static class OrderStatusBuilder implements ModelBuilder<OrderStatus> {
        private OrderStatus orderStatus;

        public OrderStatusBuilder() {
            this.orderStatus = new OrderStatus();
        }

        public OrderStatusBuilder withStatus(String status) {
            orderStatus.setStatus(status);
            return this;
        }

        @Override
        public OrderStatus build() {
            return orderStatus;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @OneToMany(mappedBy = "orderStatus")
    private List<ShoppingOrder> shoppingOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ShoppingOrder> getShoppingOrders() {
        return shoppingOrders;
    }

    public void setShoppingOrders(List<ShoppingOrder> shoppingOrders) {
        this.shoppingOrders = shoppingOrders;
    }

    public static OrderStatusBuilder create() {
        return new OrderStatusBuilder();
    }
}
