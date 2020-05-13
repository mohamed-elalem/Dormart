package com.waa.dormart.services.impl;

import com.waa.dormart.models.Review;
import com.waa.dormart.repositories.ReviewRepository;
import com.waa.dormart.services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getPendingReviews() {
        return reviewRepository.getReviewsByApprovedStatus(false);
    }

    @Override
    public List<Review> getApprovedReviews() {
        return reviewRepository.getReviewsByApprovedStatus(true);
    }

    @Override
    public List<Review> getApprovedProductReviews(Long productId) {
        return reviewRepository
                .getReviewerProductReviewsByApprovedStatus(productId, true);
    }

    @Override
    public Review approveReview(Long reviewId) {
        Review review = reviewRepository.getOne(reviewId);
        review.setApproved(true);
        reviewRepository.save(review);

        return review;
    }
}
