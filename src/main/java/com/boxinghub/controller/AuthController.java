package com.boxinghub.controller;

import com.boxinghub.entity.Member;
import com.boxinghub.entity.User;
import com.boxinghub.service.MemberService;
import com.boxinghub.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final PasswordService passwordService;

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
        if (roles.contains("ROLE_ADMIN"))   return "redirect:/admin/dashboard";
        if (roles.contains("ROLE_MEMBER"))  return "redirect:/member/dashboard";
        if (roles.contains("ROLE_TRAINER")) return "redirect:/trainer/dashboard";
        return "redirect:/login";
    }

    // ==================== ĐĂNG KÝ ====================

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("member", new Member());
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("member") Member member, RedirectAttributes ra) {
        try {
            User user = member.getUser();
            if (user != null) {
                user.setFullName(member.getFullName());
            } else {
                throw new RuntimeException("Dữ liệu tài khoản không hợp lệ!");
            }
            user.setRole("ROLE_MEMBER");
            memberService.registerNewMember(member, user);
            ra.addFlashAttribute("success", "Đăng ký thành công! Mời bạn đăng nhập.");
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute("error", "Đăng ký thất bại: " + e.getMessage());
            return "redirect:/register";
        }
    }

    // ==================== QUÊN MẬT KHẨU ====================

    /** Hiển thị form nhập email */
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "auth/forgot-password";
    }

    /** Xử lý gửi email reset */
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email,
                                        RedirectAttributes ra) {
        try {
            passwordService.initiatePasswordReset(email);
            ra.addFlashAttribute("success",
                    "Chúng tôi đã gửi link đặt lại mật khẩu đến " + email + ". Vui lòng kiểm tra hộp thư.");
        } catch (Exception e) {
            // Không tiết lộ email có tồn tại hay không vì lý do bảo mật
            ra.addFlashAttribute("success",
                    "Nếu email tồn tại trong hệ thống, bạn sẽ nhận được hướng dẫn trong vài phút.");
        }
        return "redirect:/forgot-password";
    }

    /** Hiển thị form đặt mật khẩu mới */
    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam("token") String token, Model model) {
        if (!passwordService.validateResetToken(token)) {
            model.addAttribute("error", "Link đặt lại mật khẩu không hợp lệ hoặc đã hết hạn.");
            return "auth/reset-password";
        }
        model.addAttribute("token", token);
        return "auth/reset-password";
    }

    /** Xử lý đặt mật khẩu mới */
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       RedirectAttributes ra) {
        if (!newPassword.equals(confirmPassword)) {
            ra.addFlashAttribute("error", "Mật khẩu xác nhận không khớp!");
            ra.addFlashAttribute("token", token);
            return "redirect:/reset-password?token=" + token;
        }
        if (newPassword.length() < 6) {
            ra.addFlashAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự!");
            ra.addFlashAttribute("token", token);
            return "redirect:/reset-password?token=" + token;
        }
        try {
            passwordService.resetPassword(token, newPassword);
            ra.addFlashAttribute("success", "Đặt lại mật khẩu thành công! Mời bạn đăng nhập.");
            return "redirect:/login";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/reset-password?token=" + token;
        }
    }
}