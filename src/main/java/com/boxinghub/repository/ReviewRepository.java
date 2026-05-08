package com.boxinghub.repository;

import com.boxinghub.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Lấy danh sách đánh giá của 1 HLV cụ thể
    List<Review> findByTrainerIdOrderByCreatedAtDesc(Long trainerId);

    // Kiểm tra xem Member đã đánh giá Trainer này chưa (tránh đánh giá nhiều lần)
    boolean existsByMemberIdAndTrainerId(Long memberId, Long trainerId);
    Optional<Review> findByMemberIdAndTrainerId(Long memberId, Long trainerId);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.trainer.id = :trainerId")
    Double getAverageRatingByTrainerId(@Param("trainerId") Long trainerId);
}