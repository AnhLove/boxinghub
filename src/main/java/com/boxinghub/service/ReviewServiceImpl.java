package com.boxinghub.service;

import com.boxinghub.entity.Member;
import com.boxinghub.entity.Review;
import com.boxinghub.entity.Trainer;
import com.boxinghub.repository.MemberRepository;
import com.boxinghub.repository.ReviewRepository;
import com.boxinghub.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    @Override
    @Transactional
    public Review submitReview(Long memberId, Long trainerId, int rating, String comment) {
        Optional<Review> existingReview = reviewRepository.findByMemberIdAndTrainerId(memberId, trainerId);

        Review review;
        if (existingReview.isPresent()) {
            review = existingReview.get();
        } else {
            review = new Review();
            Member member = memberRepository.findById(memberId).orElseThrow();
            Trainer trainer = trainerRepository.findById(trainerId).orElseThrow();
            review.setMember(member);
            review.setTrainer(trainer);
        }

        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());

        Review saved = reviewRepository.saveAndFlush(review); // Sử dụng saveAndFlush
        return saved;
    }

    @Override
    public List<Review> getReviewsByTrainer(Long trainerId) {
        return reviewRepository.findByTrainerIdOrderByCreatedAtDesc(trainerId);
    }

    @Override
    public boolean hasMemberReviewedTrainer(Long memberId, Long trainerId) {
        return reviewRepository.existsByMemberIdAndTrainerId(memberId, trainerId);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public Double getAverageRating(Long trainerId) {
        Double avg = reviewRepository.getAverageRatingByTrainerId(trainerId);
        return avg != null ? avg : 0.0;
    }
}