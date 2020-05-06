package com.waa.dormart.services;

import com.waa.dormart.models.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getPendingReviews();

    List<Review> getApprovedReviews();

    Review approveReview(Long reviewId);
}
