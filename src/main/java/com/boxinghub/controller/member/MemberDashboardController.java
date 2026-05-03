package com.boxinghub.controller.member;

import com.boxinghub.entity.Member;
import com.boxinghub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberDashboardController {

    private final MemberService memberService;

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        // Lấy email từ người dùng đang đăng nhập thành công
        String email = principal.getName();

        // Lấy hồ sơ cá nhân của chính học viên đó
        Member member = memberService.getMemberByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ học viên"));

        model.addAttribute("member", member);
        model.addAttribute("activePage", "dashboard");
        return "member/dashboard";
    }

    @GetMapping("/schedule")
    public String mySchedule(Model model) {
        model.addAttribute("activePage", "schedule");
        return "member/schedule"; // Trang này sẽ hiện lịch để member đăng ký tập
    }
}