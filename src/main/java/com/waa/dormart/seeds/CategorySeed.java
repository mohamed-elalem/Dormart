package com.waa.dormart.seeds;

import com.github.javafaker.Faker;
import com.waa.dormart.models.Category;
import com.waa.dormart.repositories.CategoryRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(3)
public class CategorySeed implements ApplicationRunner {
    private CategoryRepository categoryRepository;

    public CategorySeed(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Faker faker = new Faker();

        List.of("Books", "Computers").forEach(categoryName -> {
            categoryRepository.save(Category.create().withName(categoryName).withDescription(faker.lorem().sentence()).build());
        });
    }
}
