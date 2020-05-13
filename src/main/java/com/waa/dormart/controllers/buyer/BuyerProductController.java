package com.waa.dormart.controllers.buyer;

import com.waa.dormart.dto.CartProductDTO;
import com.waa.dormart.models.Product;
import com.waa.dormart.models.Review;
import com.waa.dormart.models.User;
import com.waa.dormart.services.ProductService;
import com.waa.dormart.services.ReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/marketplace/products")
public class BuyerProductController {
    private ProductService productService;
    private ReviewService reviewService;

    public BuyerProductController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping("{id}")
    public String productDetails(@PathVariable Long id,
                                 Model model,
                                 @ModelAttribute Review review,
                                 @ModelAttribute("cartProduct") CartProductDTO cartProduct,
                                 @AuthenticationPrincipal User buyer) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("reviews", reviewService.getApprovedProductReviews(id));
        return "marketplace/products/product-details";
    }

    @GetMapping("featured")
    public String getFeaturedProducts(Model model, @AuthenticationPrincipal User buyer) {
        model.addAttribute("products", productService.buyerFeaturedProducts(buyer.getId()));
        return "marketplace/products/featured-products";
    }

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("products", productService.findAllProductsFromActiveSellers());
        return "marketplace/products/list";
    }

    @PostMapping("{id}/add-review")
    public String addReviewToProduct(@Valid @ModelAttribute Review review,
                                     BindingResult result,
                                     Model model,
                                     RedirectAttributes redirectAttributes,
                                     @PathVariable("id") Long productId,
                                     @ModelAttribute("cartProduct") CartProductDTO cartProduct,
                                     @AuthenticationPrincipal User loggedInUser) {
        review.setReviewer(loggedInUser);

        if (result.hasErrors()) {
            model.addAttribute("review", review);
            model.addAttribute("product", productService.getProductById(productId));
            model.addAttribute("reviews", reviewService.getApprovedProductReviews(productId));
            return "marketplace/products/product-details";
        }

        productService.addReviewToProduct(productId, review);

        redirectAttributes.addFlashAttribute("pending-review", "Your review is waiting admin approval");
        return String.format("redirect:/marketplace/products/%d", productId);
    }
}
