package com.boxinghub.controller.member;

import com.boxinghub.entity.CreditTransaction;
import com.boxinghub.entity.Member;
import com.boxinghub.service.CreditService;
import com.boxinghub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("/member/topup")
@RequiredArgsConstructor
public class MemberTransactionController {

    private final CreditService creditService;
    private final MemberService memberService;

    @GetMapping
    public String showTopupPage(Model model) {
        model.addAttribute("activePage", "topup");
        return "member/topup/index";
    }

    @PostMapping("/confirm")
    public String confirmTopup(@RequestParam("sessions") Integer sessions, Principal principal, Model model) {
        Member member = memberService.getMemberByEmail(principal.getName()).orElseThrow();

        // Tạo lệnh nạp PENDING
        CreditTransaction tx = creditService.createPaymentRequest(member.getId(), sessions);

        model.addAttribute("transaction", tx);
        model.addAttribute("momoPhone", "0116585296666");
        model.addAttribute("accountName", "TRAN VAN TUAN ANH");

        return "member/topup/checkout";
    }
}