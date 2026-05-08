package com.boxinghub.repository;

import com.boxinghub.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Lấy các bài viết mới nhất lên đầu
    List<Post> findAllByOrderByCreatedAtDesc();
}