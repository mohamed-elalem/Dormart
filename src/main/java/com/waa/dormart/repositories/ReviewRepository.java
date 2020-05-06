package com.waa.dormart.repositories;

import com.waa.dormart.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r where r.approved = :status")
    List<Review> getReviewsByApprovedStatus(@Param("status") Boolean status);
}
