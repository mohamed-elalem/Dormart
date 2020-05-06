package com.waa.dormart.controllers.admin;

import com.waa.dormart.services.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/pending-reviews")
public class AdminReviewController {
    private ReviewService reviewService;

    public AdminReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public String getPendingReviews(Model model) {
        model.addAttribute("reviews", reviewService.getPendingReviews());
        return "admin/reviews/pending-list";
    }

    @PostMapping("{id}/approve")
    public String approveReview(@PathVariable("id") Long reviewId, RedirectAttributes redirectAttributes) {
        reviewService.approveReview(reviewId);

        redirectAttributes.addFlashAttribute("flashMessage", "Review has been approved successfully");

        return "redirect:/admin/pending-reviews/";
    }
}
