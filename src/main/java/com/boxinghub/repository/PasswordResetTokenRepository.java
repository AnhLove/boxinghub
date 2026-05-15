package com.boxinghub.repository;

import com.boxinghub.entity.PasswordResetToken;
import com.boxinghub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    // Xóa token cũ của user trước khi tạo token mới
    void deleteByUser(User user);
}