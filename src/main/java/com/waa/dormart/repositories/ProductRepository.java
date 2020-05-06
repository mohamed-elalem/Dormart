package com.waa.dormart.repositories;

import com.waa.dormart.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p from Product p where p.seller.id = :sellerId")
    List<Product> getSellerProducts(@Param("sellerId") Long sellerId);

    @Query("SELECT p from Product p JOIN FETCH p.seller s JOIN FETCH s.followers f WHERE f.id = :buyerId AND s.active = true    ")
    Set<Product> getBuyerFeaturedProducts(@Param("buyerId") Long buyerId);

    @Query("SELECT p from Product p JOIN FETCH p.seller s WHERE s.active = :activeStatus")
    Set<Product> findAllProductsBySellerActiveStatus(@Param("activeStatus") Boolean activeStatus);
}
