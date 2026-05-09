package com.boxinghub.repository;

import com.boxinghub.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Tìm các bình luận theo ID bài viết (Nếu sau này bạn muốn lấy riêng comment qua API)
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);

    // Đếm số lượng bình luận của một bài viết
    long countByPostId(Long postId);
}