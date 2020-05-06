package com.waa.dormart.services;

import com.waa.dormart.models.Product;
import com.waa.dormart.models.Review;

import java.util.List;
import java.util.Set;

public interface ProductService {
    List<Product> getSellerProducts(Long sellerId);
    Product getProductById(Long id);
    Product saveProduct(Product product);
    Product deleteProduct(Long id);
    List<Product> findAll();
    Set<Product> buyerFeaturedProducts(Long buyerId);
    Set<Product> findAllProductsFromActiveSellers();

    Product addReviewToProduct(Long productId, Review review);
}
