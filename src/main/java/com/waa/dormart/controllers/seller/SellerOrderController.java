package com.waa.dormart.controllers.seller;

import com.waa.dormart.constants.OrderStatusEnum;
import com.waa.dormart.models.ShoppingOrder;
import com.waa.dormart.models.User;
import com.waa.dormart.repositories.ShoppingOrderRepository;
import com.waa.dormart.services.ShoppingOrderService;
import com.waa.dormart.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/seller/orders")
public class SellerOrderController {
    private ShoppingOrderService shoppingOrderService;
    private UserService userService;

    public SellerOrderController(ShoppingOrderService shoppingOrderService, UserService userService) {
        this.shoppingOrderService = shoppingOrderService;
        this.userService = userService;
    }

    @GetMapping
    public String getOrders(@AuthenticationPrincipal User loggedInUser, Model model) {
        List<ShoppingOrder> orders = shoppingOrderService.getSellerOrders(loggedInUser.getId());
        model.addAttribute("pendingOrders", orders.stream()
            .filter(order -> order.getOrderStatus().getStatus().equals(OrderStatusEnum.PENDING.name()))
            .collect(Collectors.toList()));
        model.addAttribute("shippedOrders", orders.stream()
                .filter(order -> order.getOrderStatus().getStatus().equals(OrderStatusEnum.SHIPPED.name()))
                .collect(Collectors.toList()));
        model.addAttribute("onTheWayOrders", orders.stream()
                .filter(order -> order.getOrderStatus().getStatus().equals(OrderStatusEnum.ON_THE_WAY.name()))
                .collect(Collectors.toList()));
        model.addAttribute("deliveredOrders", orders.stream()
                .filter(order -> order.getOrderStatus().getStatus().equals(OrderStatusEnum.DELIVERED.name()))
                .collect(Collectors.toList()));
        model.addAttribute("returnedOrders", orders.stream()
                .filter(order -> order.getOrderStatus().getStatus().equals(OrderStatusEnum.RETURNED.name()))
                .collect(Collectors.toList()));

        return "sellers/orders/list";
    }

    @PostMapping("{id}/ship")
    public String shipOrder(@PathVariable Long id) {
        shoppingOrderService.changeOrderStatus(id, OrderStatusEnum.SHIPPED);

        return "redirect:/seller/orders";
    }

    @PostMapping("{id}/dispatch")
    public String dispatchOrder(@PathVariable Long id) {
        shoppingOrderService.changeOrderStatus(id, OrderStatusEnum.ON_THE_WAY);

        return "redirect:/seller/orders";
    }

    @PostMapping("{id}/deliver")
    public String deliverOrder(@PathVariable Long id) {
        shoppingOrderService.changeOrderStatus(id, OrderStatusEnum.DELIVERED);
        userService.addPurchasePoints(id);
        return "redirect:/seller/orders";
    }


    @PostMapping("{id}/cancel")
    public String cancelOrder(@PathVariable Long id) {
        shoppingOrderService.changeOrderStatus(id, OrderStatusEnum.RETURNED);

        return "redirect:/seller/orders";
    }
}
