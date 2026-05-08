package com.boxinghub.service;

import com.boxinghub.entity.Review;
import java.util.List;

public interface ReviewService {

    // Member gửi đánh giá mới
    Review submitReview(Long memberId, Long trainerId, int rating, String comment);

    // Lấy tất cả đánh giá của một Huấn luyện viên (Hiển thị ở trang Trainer Detail)
    List<Review> getReviewsByTrainer(Long trainerId);

    // Kiểm tra xem Member đã đánh giá HLV này chưa (Để ẩn/hiện nút đánh giá)
    boolean hasMemberReviewedTrainer(Long memberId, Long trainerId);

    // Admin quản lý: Xóa đánh giá nếu vi phạm tiêu chuẩn
    void deleteReview(Long reviewId);

    // Tính điểm trung bình của Trainer
    Double getAverageRating(Long trainerId);
}