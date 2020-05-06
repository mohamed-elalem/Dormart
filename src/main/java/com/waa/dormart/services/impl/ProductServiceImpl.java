package com.waa.dormart.services.impl;

import com.waa.dormart.models.Product;
import com.waa.dormart.models.Review;
import com.waa.dormart.repositories.ProductRepository;
import com.waa.dormart.repositories.ReviewRepository;
import com.waa.dormart.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;

    public ProductServiceImpl(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
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

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Set<Product> buyerFeaturedProducts(Long buyerId) {
        return productRepository.getBuyerFeaturedProducts(buyerId);
    }

    @Override
    public Set<Product> findAllProductsFromActiveSellers() {
        return productRepository.findAllProductsBySellerActiveStatus(true);
    }

    @Override
    public Product addReviewToProduct(Long productId, Review review) {
        Product product = productRepository.findById(productId).get();
        review.setProduct(product);

        reviewRepository.save(review);
        return product;
    }
}
