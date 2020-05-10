package com.waa.dormart.controllers.buyer;

import com.waa.dormart.dto.CartDTO;
import com.waa.dormart.dto.CartProductDTO;
import com.waa.dormart.dto.OrderInformationDTO;
import com.waa.dormart.models.Item;
import com.waa.dormart.models.Product;
import com.waa.dormart.models.User;
import com.waa.dormart.services.ShoppingOrderService;
import com.waa.dormart.services.ProductService;
import com.waa.dormart.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@SessionAttributes({"cart"})
@RequestMapping("/marketplace/cart")
public class BuyerShoppingCartController {

    private ProductService productService;
    private ShoppingOrderService shoppingOrderService;
    private UserService userService;

    public BuyerShoppingCartController(ProductService productService,
                                       ShoppingOrderService shoppingOrderService,
                                       UserService userService) {
        this.productService = productService;
        this.shoppingOrderService = shoppingOrderService;
        this.userService = userService;
    }

    @PostMapping("add-product")
    public String addProduct(CartProductDTO cartProduct, HttpSession httpSession, Model model) {
        CartDTO cart;
        if (httpSession.getAttribute("cart") == null) {
            cart = new CartDTO();
        } else {
            cart = (CartDTO) httpSession.getAttribute("cart");
        }

        Product product = productService.getProductById(cartProduct.getProductId());

        Item item = Item.create()
                .withProduct(product)
                .withQuantity(cartProduct.getQuantity())
                .build();

        cart.addItem(item);

        model.addAttribute("cart", cart);

        return "redirect:/marketplace/cart";
    }

    @PostMapping("{id}/remove")
    public String removeProduct(@PathVariable("id") Integer index, HttpSession httpSession) {
        CartDTO cart = (CartDTO) httpSession.getAttribute("cart");
        cart.removeItem(index);
        return "redirect:/marketplace/cart";
    }

    @GetMapping
    public String getCart(Model model, HttpSession httpSession, @AuthenticationPrincipal User loggedInUser) {
        CartDTO cart;
        if (httpSession.getAttribute("cart") == null) {
            cart = new CartDTO();
            model.addAttribute("cart", cart);
        } else {
            cart = (CartDTO) httpSession.getAttribute("cart");
        }

        Double totalPrice = cart.getItems().stream()
                .map(item -> item.getQuantity() * item.getProduct().getPrice())
                .reduce(0.0, (total, price) -> total + price);

        User buyer = userService.getUser(loggedInUser.getId());
        model.addAttribute("totalPrice", totalPrice);
        if (buyer.getPoints().longValue() > 0) {
            Double discountedPrice = totalPrice - Math.min(buyer.getPoints(), totalPrice);
            model.addAttribute("discountedPrice", discountedPrice);
        }
        return "marketplace/cart/index";
    }

    @GetMapping("checkout")
    public String checkoutForm(@ModelAttribute("orderInfo") OrderInformationDTO orderInfo) {
        return "marketplace/cart/checkout-form";
    }

    @PostMapping("checkout")
    public String checkout(@Valid @ModelAttribute("orderInfo") OrderInformationDTO orderInfo,
                           BindingResult bindingResult,
                           SessionStatus status,
                           @AuthenticationPrincipal User buyer,
                           HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "marketplace/cart/checkout-form";
        }

        shoppingOrderService.createOrders(buyer.getId(), orderInfo, (CartDTO) httpSession.getAttribute("cart"));

        status.setComplete();
        return "redirect:/marketplace/orders";
    }
}
