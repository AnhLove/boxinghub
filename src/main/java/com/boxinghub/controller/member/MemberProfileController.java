package com.boxinghub.controller.member;

import com.boxinghub.entity.Member;
import com.boxinghub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/member/profile")
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberService memberService;

    // Hiển thị trang hồ sơ
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

    // Xử lý cập nhật thông tin
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("member") Member memberData,
                                Principal principal,
                                RedirectAttributes ra) {
        try {
            // Bạn cần thêm hàm updateProfile này vào MemberService như tôi đã gợi ý ở trên
            memberService.updateProfile(principal.getName(), memberData);
            ra.addFlashAttribute("success", "Cập nhật thông tin cá nhân thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/member/profile";
    }
}