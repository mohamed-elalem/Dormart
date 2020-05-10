package com.waa.dormart.controllers.buyer;

import com.waa.dormart.constants.OrderStatusEnum;
import com.waa.dormart.models.OrderStatus;
import com.waa.dormart.models.ShoppingOrder;
import com.waa.dormart.models.User;
import com.waa.dormart.pdf.ReceiptPdf;
import com.waa.dormart.services.ShoppingOrderService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/marketplace/orders")
public class BuyerOrderController {
    private ShoppingOrderService shoppingOrderService;

    public BuyerOrderController(ShoppingOrderService shoppingOrderService) {
        this.shoppingOrderService = shoppingOrderService;
    }

    @GetMapping
    public String getOrders(@AuthenticationPrincipal User loggedInUser, Model model) {
        List<ShoppingOrder> shoppingOrders = shoppingOrderService.getBuyerOrders(loggedInUser.getId());
        model.addAttribute("pendingOrders", shoppingOrders.stream()
                .filter(order ->
                        !order.getOrderStatus().getStatus().equals(OrderStatusEnum.RETURNED.name()) &&
                                !order.getOrderStatus().getStatus().equals(OrderStatusEnum.DELIVERED.name())
                ).collect(Collectors.toList()));
        model.addAttribute("deliveredOrders", shoppingOrders.stream()
                .filter(order -> order.getOrderStatus().getStatus().equals(OrderStatusEnum.DELIVERED.name()))
                .collect(Collectors.toList()));
        model.addAttribute("returnedOrders", shoppingOrders.stream()
                .filter(order -> order.getOrderStatus().getStatus().equals(OrderStatusEnum.RETURNED.name()))
                .collect(Collectors.toList()));

        return "marketplace/orders/list";
    }

    @PostMapping("{id}/cancel")
    public String cancelOrder(@PathVariable("id") Long id) {
        shoppingOrderService.changeOrderStatus(id, OrderStatusEnum.RETURNED);

        return "redirect:/marketplace/orders";
    }

    @GetMapping("{id}/receipt")
    public HttpEntity<InputStreamResource> receiptPdf(@PathVariable Long id) {
        ShoppingOrder order = shoppingOrderService.getOrderById(id);
        ByteArrayInputStream bis = ReceiptPdf.render(order);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=receipt.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
