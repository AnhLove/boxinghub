package com.boxinghub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "posts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Post extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member author; // Người đăng bài

    @Column(nullable = false)
    private String title; // Tiêu đề bài viết

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // Nội dung bài viết

    private String imageUrl; // Ảnh đính kèm (nếu có)

    // Bạn có thể thêm lượt thích hoặc trạng thái ẩn/hiện
    private Integer likes = 0;
    private boolean isHidden = false;
}