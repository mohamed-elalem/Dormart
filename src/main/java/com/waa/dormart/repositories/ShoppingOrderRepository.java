package com.waa.dormart.repositories;

import com.waa.dormart.models.ShoppingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoppingOrderRepository extends JpaRepository<ShoppingOrder, Long> {
    @Query("SELECT o from ShoppingOrder o JOIN FETCH o.buyer b where b.id = :buyerId")
    List<ShoppingOrder> findAllByBuyerId(@Param("buyerId") Long buyerId);

    @Query("SELECT o from ShoppingOrder o JOIN FETCH o.seller s where s.id = :sellerId")
    List<ShoppingOrder> findAllBySellerId(@Param("sellerId") Long sellerId);

    @Query("SELECT o from ShoppingOrder o JOIN FETCH o.orderStatus os JOIN FETCH o.items i JOIN FETCH i.product p WHERE p.id = :productId AND os.status = :status")
    List<ShoppingOrder> findAllByProductIdAnAndOrderStatus(@Param("productId") Long productId, @Param("status") String status);
}
