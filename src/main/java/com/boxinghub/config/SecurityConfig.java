package com.boxinghub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/vendor/**").permitAll()
                        .requestMatchers("/", "/login", "/register", "/error").permitAll()

                        // Phân quyền rạch ròi
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/member/**").hasRole("MEMBER")
                        .requestMatchers("/trainer/**").hasRole("TRAINER")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        // Xóa cái defaultSuccessUrl cũ đi
                        .successHandler((request, response, authentication) -> {
                            // Logic điều hướng dựa trên Role
                            var authorities = authentication.getAuthorities();
                            for (var auth : authorities) {
                                if (auth.getAuthority().equals("ROLE_ADMIN")) {
                                    response.sendRedirect("/admin/dashboard");
                                    return;
                                } else if (auth.getAuthority().equals("ROLE_MEMBER")) {
                                    response.sendRedirect("/member/dashboard");
                                    return;
                                }
                            }
                            response.sendRedirect("/");
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}