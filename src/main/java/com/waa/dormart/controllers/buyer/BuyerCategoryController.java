package com.waa.dormart.controllers.buyer;

import com.waa.dormart.models.Category;
import com.waa.dormart.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/marketplace/categories")
public class BuyerCategoryController {
    private CategoryService categoryService;

    public BuyerCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "marketplace/categories/index";
    }

    @GetMapping("{id}")
    public String getCategoryProducts(Model model, @PathVariable Long id) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "marketplace/categories/category-products";
    }
}
