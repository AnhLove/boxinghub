package com.boxinghub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import com.boxinghub.entity.Member;
import com.boxinghub.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final com.boxinghub.service.MemberService memberService;

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

    // 1. Hiển thị trang đăng ký
    @GetMapping("/register")
    public String registerPage(Model model) {
        // Truyền đối tượng trống để Form bind dữ liệu
        model.addAttribute("member", new Member());
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // 2. Xử lý dữ liệu gửi lên từ Form đăng ký
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("member") Member member, RedirectAttributes ra) {
        try {
            User user = member.getUser();

            // 1. ĐỒNG BỘ DỮ LIỆU: Vì User cũng yêu cầu fullName không được null
            if (user != null) {
                user.setFullName(member.getFullName());
            } else {
                throw new RuntimeException("Dữ liệu tài khoản không hợp lệ!");
            }

            // 2. Gán Role mặc định nếu Service chưa làm việc này
            user.setRole("ROLE_MEMBER");

            // 3. Gọi service để mã hóa mật khẩu và lưu cả 2
            memberService.registerNewMember(member, user);

            ra.addFlashAttribute("success", "Đăng ký thành công! Mời bạn đăng nhập.");
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace(); // In ra console để bạn debug nếu vẫn lỗi
            ra.addFlashAttribute("error", "Đăng ký thất bại: " + e.getMessage());
            return "redirect:/register";
        }
    }
}