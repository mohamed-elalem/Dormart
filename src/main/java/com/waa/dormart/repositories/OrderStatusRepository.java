package com.waa.dormart.repositories;

import com.waa.dormart.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    OrderStatus findByStatus(String status);
}
