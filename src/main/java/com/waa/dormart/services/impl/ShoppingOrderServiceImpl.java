package com.waa.dormart.services.impl;

import com.waa.dormart.constants.OrderStatusEnum;
import com.waa.dormart.dto.CartDTO;
import com.waa.dormart.dto.OrderInformationDTO;
import com.waa.dormart.models.ShoppingOrder;
import com.waa.dormart.models.OrderStatus;
import com.waa.dormart.models.Product;
import com.waa.dormart.models.User;
import com.waa.dormart.repositories.OrderStatusRepository;
import com.waa.dormart.repositories.ProductRepository;
import com.waa.dormart.repositories.ShoppingOrderRepository;
import com.waa.dormart.repositories.UserRepository;
import com.waa.dormart.services.ShoppingOrderService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingOrderServiceImpl implements ShoppingOrderService {
    private ShoppingOrderRepository shoppingOrderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private OrderStatusRepository orderStatusRepository;

    public ShoppingOrderServiceImpl(ShoppingOrderRepository shoppingOrderRepository,
                                    UserRepository userRepository,
                                    OrderStatusRepository orderStatusRepository,
                                    ProductRepository productRepository) {
        this.shoppingOrderRepository = shoppingOrderRepository;
        this.userRepository = userRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createOrders(Long buyerId, OrderInformationDTO orderInfo, CartDTO cartDTO) {
        Map<Long, ShoppingOrder> ordersMap = new HashMap<>();

        final User buyer = userRepository.findById(buyerId).get();
        final OrderStatus pendingStatus = orderStatusRepository.findByStatus(OrderStatusEnum.PENDING.name());

        cartDTO.getItems().forEach(item -> {
            User seller = item.getProduct().getSeller();
            ShoppingOrder shoppingOrder = ordersMap.getOrDefault(seller.getId(), ShoppingOrder.create()
                                                                        .withBuyer(buyer)
                                                                        .withSeller(seller)
                                                                        .withOrderStatus(pendingStatus)
                                                                        .withPrice(0.0)
                                                                        .withCardNumber(orderInfo.getCardNumber())
                                                                        .withCvv(orderInfo.getCvv())
                                                                        .withCity(orderInfo.getCity())
                                                                        .withState(orderInfo.getState())
                                                                        .withZipcode(orderInfo.getZipcode())
                                                                        .build());

            shoppingOrder.getItems().add(item);
            item.setShoppingOrder(shoppingOrder);
            Double itemPrice = item.getQuantity() * item.getProduct().getPrice();
            itemPrice -= Math.min(itemPrice, Math.floor(buyer.getPoints()));;
            shoppingOrder.setPrice(shoppingOrder.getPrice() + itemPrice);
            ordersMap.put(seller.getId(), shoppingOrder);
        });

        ordersMap.forEach((sellerId, order) -> {
            shoppingOrderRepository.save(order);
            order.getItems().forEach(item -> {
                Product product = item.getProduct();
                product.setQuantity(product.getQuantity() - item.getQuantity());
                productRepository.save(product);
            });
        });


    }

    @Override
    public List<ShoppingOrder> getBuyerOrders(Long buyerId) {
        return shoppingOrderRepository.findAllByBuyerId(buyerId);
    }

    @Override
    public List<ShoppingOrder> getSellerOrders(Long sellerId) {
        return shoppingOrderRepository.findAllBySellerId(sellerId);
    }

    @Override
    public void changeOrderStatus(Long orderId, OrderStatusEnum status) {
        ShoppingOrder order = shoppingOrderRepository.getOne(orderId);
        OrderStatus orderStatus = orderStatusRepository.findByStatus(status.name());
        order.setOrderStatus(orderStatus);

        shoppingOrderRepository.save(order);
    }

    @Override
    public ShoppingOrder getOrderById(Long id) {
        return shoppingOrderRepository.getOne(id);
    }
}
