package com.boxinghub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    // Điều hướng thông minh tại trang chủ dựa trên Role
    @GetMapping("/")
    public String root(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        } else if (roles.contains("ROLE_MEMBER")) {
            return "redirect:/member/dashboard";
        } else if (roles.contains("ROLE_TRAINER")) {
            return "redirect:/trainer/dashboard";
        }

        return "redirect:/login";
    }
}