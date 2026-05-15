package com.boxinghub.controller.member;

import com.boxinghub.entity.Member;
import com.boxinghub.service.MemberService;
import com.boxinghub.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/member/profile")
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberService memberService;
    private final PasswordService passwordService;

    @GetMapping
    @Transactional(readOnly = true)
    public String showProfile(Principal principal, Model model) {
        String email = principal.getName();
        Member member = memberService.getMemberByEmail(email)
                .orElseThrow(() -> new RuntimeException("Hồ sơ không tồn tại cho email: " + email));

        model.addAttribute("member", member);
        model.addAttribute("activePage", "profile");
        model.addAttribute("pageTitle", "Hồ sơ cá nhân");

        return "member/profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("member") Member memberData,
                                @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
                                Principal principal,
                                RedirectAttributes ra) {
        try {
            memberService.updateProfile(principal.getName(), memberData, avatarFile);
            ra.addFlashAttribute("success", "Cập nhật hồ sơ thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/member/profile";
    }

    /**
     * Đổi mật khẩu từ trang Profile (yêu cầu nhập mật khẩu hiện tại)
     */
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal,
                                 RedirectAttributes ra) {
        // Kiểm tra mật khẩu xác nhận
        if (!newPassword.equals(confirmPassword)) {
            ra.addFlashAttribute("passwordError", "Mật khẩu mới và xác nhận không khớp!");
            return "redirect:/member/profile";
        }
        // Kiểm tra độ dài
        if (newPassword.length() < 6) {
            ra.addFlashAttribute("passwordError", "Mật khẩu mới phải có ít nhất 6 ký tự!");
            return "redirect:/member/profile";
        }
        try {
            passwordService.changePassword(principal.getName(), currentPassword, newPassword);
            ra.addFlashAttribute("passwordSuccess", "Đổi mật khẩu thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("passwordError", e.getMessage());
        }
        return "redirect:/member/profile";
    }
}