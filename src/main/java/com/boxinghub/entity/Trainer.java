package com.boxinghub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "trainers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trainer extends BaseEntity {

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "specialization")
    private String specialization; // "Boxing", "Muay Thai", "Fitness"

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(columnDefinition = "TEXT")
    private String bio; // Giới thiệu bản thân

    @Enumerated(EnumType.STRING)
    private TrainerStatus status; // ACTIVE, INACTIVE

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<Review> reviews = new java.util.ArrayList<>();

    // Hàm tiện ích để tính điểm trung bình (optional)
    public Double getAverageRating() {
        if (reviews == null || reviews.isEmpty()) return 0.0;
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }
}