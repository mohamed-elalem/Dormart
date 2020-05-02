package com.waa.dormart.controllers.seller;

import com.waa.dormart.models.Product;
import com.waa.dormart.models.User;
import com.waa.dormart.services.CategoryService;
import com.waa.dormart.services.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/seller/products")
public class SellerProductController {
    private ProductService productService;
    private CategoryService categoryService;

    public SellerProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAllProducts(Model model, @AuthenticationPrincipal User seller) {
        List<Product> products = productService.getSellerProducts(seller.getId());
        model.addAttribute("products", products);
        return "sellers/products/index";
    }

    @PostMapping("{id}/delete")
    public String deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/seller/products";
    }

    @GetMapping("{id}")
    public String getProductDetails(@PathVariable("id") Long productId, Model model) {
        model.addAttribute("product", this.productService.getProductById(productId));
        return "sellers/products/product-details";
    }

    @GetMapping("new")
    public String getProductForm(@AuthenticationPrincipal User seller, @ModelAttribute Product product, Model model) {
        product.setSeller(seller);
        model.addAttribute(product);
        model.addAttribute("categories", categoryService.findAll());
        return "sellers/products/new";
    }

    @PostMapping
    public String save(@AuthenticationPrincipal User seller,
                       @Valid @ModelAttribute Product product,
                       BindingResult result,
                       Model model) {
        product.setSeller(seller);
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "sellers/products/new";
        }

        this.productService.saveProduct(product);

        return "redirect:/seller/products";
    }

    @GetMapping("{id}/edit")
    public String editProductForm(@PathVariable("id") Long productId, Model model) {
        model.addAttribute("product", productService.getProductById(productId));
        model.addAttribute("categories", categoryService.findAll());
        return "sellers/products/edit";
    }

    @PostMapping("{id}/edit")
    public String udpateProduct(@AuthenticationPrincipal User seller,
                       @Valid @ModelAttribute Product product,
                       BindingResult result,
                       Model model,
                       @PathVariable Long id) {
        product.setId(id);
        product.setSeller(seller);
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "sellers/products/new";
        }

        this.productService.saveProduct(product);

        return "redirect:/seller/products";
    }
}
