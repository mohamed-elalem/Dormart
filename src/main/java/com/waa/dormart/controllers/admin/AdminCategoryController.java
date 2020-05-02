package com.waa.dormart.controllers.admin;

import com.waa.dormart.models.Category;
import com.waa.dormart.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/categories/list";
    }

    @PostMapping("{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("new")
    public String createNewCategory(@ModelAttribute Category category, Model model) {
        model.addAttribute(category);
        return "/admin/categories/new";
    }

    @PostMapping
    public String saveNewCategory(@Valid @ModelAttribute Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(category);
            return "/admin/categories/new";
        }

        categoryService.save(category);
        return "redirect:/admin/categories";
    }
}
