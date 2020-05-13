package com.waa.dormart.controllers.seller;

import com.waa.dormart.exceptions.HttpException;
import com.waa.dormart.exceptions.UnuthorizedException;
import com.waa.dormart.models.Product;
import com.waa.dormart.models.ShoppingOrder;
import com.waa.dormart.models.User;
import com.waa.dormart.services.CategoryService;
import com.waa.dormart.services.ProductService;
import com.waa.dormart.services.ShoppingOrderService;
import com.waa.dormart.services.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
@RequestMapping("/seller/products")
public class SellerProductController {
    private ProductService productService;
    private CategoryService categoryService;
    private StorageService storageService;
    private ShoppingOrderService shoppingOrderService;

    public SellerProductController(ProductService productService,
                                   CategoryService categoryService,
                                   StorageService storageService,
                                   ShoppingOrderService shoppingOrderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.storageService = storageService;
        this.shoppingOrderService = shoppingOrderService;
    }

    @GetMapping
    public String getAllProducts(Model model, @AuthenticationPrincipal User seller) {
        List<Product> products = productService.getSellerProducts(seller.getId());
        model.addAttribute("products", products);
        return "sellers/products/index";
    }

    @PostMapping("{id}/delete")
    public String deleteProduct(@PathVariable("id") Long productId) {
        List<ShoppingOrder> orders = shoppingOrderService.getDeliveredProductOrders(productId);
        if (orders.size() > 0) {
            throw new UnuthorizedException(HttpStatus.UNAUTHORIZED, "Product cannot be deleted because it is already purchased");
        }
        productService.deleteProduct(productId);
        return "redirect:/seller/products";
    }

    @GetMapping("{id}")
    public String getProductDetails(@PathVariable("id") Long productId, Model model) {
        model.addAttribute("product", this.productService.getProductById(productId));
        return "sellers/products/product-details";
    }

    @GetMapping("new")
    public String getProductForm(@AuthenticationPrincipal User seller,
                                 @ModelAttribute Product product, Model model) {
        product.setSeller(seller);
        model.addAttribute(product);
        model.addAttribute("categories", categoryService.findAll());
        return "sellers/products/new";
    }

    @PostMapping("new")
    public String save(@AuthenticationPrincipal User seller,
                       @Valid @ModelAttribute Product product,
                       BindingResult result,
                       Model model,
                       @RequestPart("image") MultipartFile image) {
        if (!seller.getActive()) {
            throw (HttpException) new UnuthorizedException(HttpStatus.UNAUTHORIZED, "You cannot post any product until the admin approves your account");
        }
        product.setSeller(seller);

        if (image == null) {
            result.addError(new ObjectError("image", "{model.notBlank.error}"));
        }

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "sellers/products/new";
        }
        String filename = String.format("%s.%s", System.currentTimeMillis(), image.getContentType().split("/")[1]);
        storageService.store(image, filename);

        product.setImage(filename);

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
    public String updateProduct(@AuthenticationPrincipal User seller,
                       @Valid @ModelAttribute Product product,
                       BindingResult result,
                       Model model,
                       @RequestPart("image") MultipartFile image,
                       @PathVariable Long id) {
        product.setId(id);
        product.setSeller(seller);
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "sellers/products/new";
        }

        if (image != null) {
            String filename = String.format("%s.%s", System.currentTimeMillis(), image.getContentType().split("/")[1]);
            storageService.store(image, filename);

            product.setImage(filename);
        }

        this.productService.saveProduct(product);

        return "redirect:/seller/products";
    }

    @InitBinder
    public void initializeBinder(WebDataBinder binder) {
        binder.setDisallowedFields("image");
    }
}
