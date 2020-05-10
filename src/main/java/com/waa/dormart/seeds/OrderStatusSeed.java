package com.waa.dormart.seeds;

import com.waa.dormart.constants.OrderStatusEnum;
import com.waa.dormart.models.OrderStatus;
import com.waa.dormart.repositories.OrderStatusRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
public class OrderStatusSeed implements ApplicationRunner {
    private OrderStatusRepository orderStatusRepository;

    public OrderStatusSeed(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List.of(OrderStatusEnum.values()).forEach(orderStatusEnum -> {
            orderStatusRepository.save(OrderStatus.create().withStatus(orderStatusEnum.name()).build());
        });
    }
}
