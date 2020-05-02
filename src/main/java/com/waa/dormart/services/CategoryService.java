package com.waa.dormart.services;

import com.waa.dormart.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category delete(Long categoryId);
    Category save(Category category);
}
