package com.waa.dormart.services;

import com.waa.dormart.constants.OrderStatusEnum;
import com.waa.dormart.dto.CartDTO;
import com.waa.dormart.dto.OrderInformationDTO;
import com.waa.dormart.models.ShoppingOrder;

import java.util.List;

public interface ShoppingOrderService {
    void createOrders(Long buyerId, OrderInformationDTO orderInfo, CartDTO cartDTO);
    List<ShoppingOrder> getBuyerOrders(Long buyerId);
    List<ShoppingOrder> getSellerOrders(Long sellerId);
    void changeOrderStatus(Long orderId, OrderStatusEnum status);

    ShoppingOrder getOrderById(Long id);
}
