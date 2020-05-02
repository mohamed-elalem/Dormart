package com.waa.dormart.services.impl;

import com.waa.dormart.exceptions.DeleteUnuthorized;
import com.waa.dormart.exceptions.HttpException;
import com.waa.dormart.models.Category;
import com.waa.dormart.repositories.CategoryRepository;
import com.waa.dormart.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category delete(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        try {
            categoryRepository.delete(category);
        } catch (Exception ex) {
            throw (HttpException) new DeleteUnuthorized(HttpStatus.FORBIDDEN, "Category already products");
        }
        return category;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
