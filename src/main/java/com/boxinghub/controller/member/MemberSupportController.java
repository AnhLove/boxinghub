package com.boxinghub.controller.member;

import org.springframework.ui.Model;
import com.boxinghub.entity.Member;
import com.boxinghub.service.MemberService;
import com.boxinghub.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/member/support")
@RequiredArgsConstructor
public class MemberSupportController {
    private final SupportService supportService;
    private final MemberService memberService;

    @GetMapping
    public String supportPage(Principal principal, Model model) {
        Member member = memberService.getMemberByEmail(principal.getName()).orElseThrow();
        model.addAttribute("tickets", supportService.getMemberTickets(member.getId()));
        model.addAttribute("activePage", "support");
        return "member/support"; // Bạn sẽ cần tạo file support.html
    }

    @PostMapping("/send")
    public String sendTicket(@RequestParam String title,
                             @RequestParam String category,
                             @RequestParam String content,
                             Principal principal,
                             RedirectAttributes ra) {
        Member member = memberService.getMemberByEmail(principal.getName()).orElseThrow();
        supportService.createTicket(member.getId(), title, category, content);
        ra.addFlashAttribute("success", "Gửi yêu cầu hỗ trợ thành công!");
        return "redirect:/member/support";
    }
}