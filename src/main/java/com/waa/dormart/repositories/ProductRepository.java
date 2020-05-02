package com.waa.dormart.repositories;

import com.waa.dormart.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p from Product p where p.seller.id = :sellerId")
    List<Product> getSellerProducts(@Param("sellerId") Long sellerId);
}
