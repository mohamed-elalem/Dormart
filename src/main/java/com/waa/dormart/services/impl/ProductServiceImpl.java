package com.waa.dormart.services.impl;

import com.waa.dormart.models.Product;
import com.waa.dormart.repositories.ProductRepository;
import com.waa.dormart.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getSellerProducts(Long sellerId) {
        return productRepository.getSellerProducts(sellerId);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Product saveProduct(Product product) {
        product = productRepository.save(product);
        return product;
    }

    @Override
    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
        return product;
    }
}
