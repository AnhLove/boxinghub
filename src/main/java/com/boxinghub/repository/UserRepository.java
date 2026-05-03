package com.boxinghub.repository;

import com.boxinghub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm kiếm tài khoản bằng email để đăng nhập
    Optional<User> findByEmail(String email);
}