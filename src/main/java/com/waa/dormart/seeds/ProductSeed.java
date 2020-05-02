package com.waa.dormart.seeds;

import com.github.javafaker.Faker;
import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.Category;
import com.waa.dormart.models.Product;
import com.waa.dormart.models.User;
import com.waa.dormart.repositories.CategoryRepository;
import com.waa.dormart.repositories.ProductRepository;
import com.waa.dormart.repositories.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class ProductSeed implements ApplicationRunner {
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    public ProductSeed(UserRepository userRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User seller = userRepository.findAllByRoleAndActive(RoleEnum.SELLER.roleName(), true).get(0);
        Category category = categoryRepository.findAll().get(0);
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            productRepository.save(Product.create()
                .withName(faker.book().title())
                .withDescription(faker.hitchhikersGuideToTheGalaxy().marvinQuote())
                .withSeller(seller)
                .withPrice(faker.number().randomDouble(4, 0, 9999))
                .withQuantity(faker.random().nextInt(300) + 1)
                .withCategory(category)
                .build());
        }
    }
}
