package com.waa.dormart.services;

import com.waa.dormart.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getSellerProducts(Long sellerId);

    Product getProductById(Long id);

    Product saveProduct(Product product);

    Product deleteProduct(Long id);
}
