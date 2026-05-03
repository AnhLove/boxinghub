package com.boxinghub;

import com.boxinghub.entity.User;
import com.boxinghub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class BoxinghubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoxinghubApplication.class, args);
	}

	@Bean
	CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Kiểm tra nếu chưa có admin thì mới tạo
			if (userRepository.findByEmail("admin@boxinghub.com").isEmpty()) {
				User admin = new User();
				admin.setEmail("admin@boxinghub.com");
				admin.setPassword(passwordEncoder.encode("admin123")); // Mật khẩu đăng nhập
				admin.setRole("ROLE_ADMIN");
				userRepository.save(admin);
				System.out.println("--- Đã tạo tài khoản Admin mặc định: admin@boxinghub.com / admin123 ---");
			}
		};
	}
}