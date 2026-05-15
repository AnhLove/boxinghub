package com.boxinghub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

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
                        .requestMatchers("/", "/login", "/register", "/error", "/forgot-password", "/reset-password").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/member/**").hasRole("MEMBER")
                        .requestMatchers("/trainer/**").hasRole("TRAINER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
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

                .rememberMe(remember -> remember
                        .key("boxinghub_secret_key")
                        .tokenValiditySeconds(86400 * 7)
                        .userDetailsService(userDetailsService)
                        .rememberMeParameter("remember-me")
                )
                // -------------------------
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                );

        return http.build();
    }
}