package com.boxinghub.controller.admin;

import com.boxinghub.service.CreditService;
import com.boxinghub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/transactions")
@RequiredArgsConstructor
public class AdminTransactionController {

    private final CreditService creditService;
    private final MemberService memberService;

    @GetMapping
    public String showTransactionPage(Model model) {
        // Lấy danh sách tất cả giao dịch để hiển thị bảng lịch sử
        model.addAttribute("transactions", creditService.getAllTransactions());

        // Lấy danh sách học viên để đổ vào dropdown/select trong form nạp
        model.addAttribute("members", memberService.getAllMembers());

        model.addAttribute("activePage", "transactions");
        model.addAttribute("pageTitle", "Quản lý nạp buổi tập");

        return "admin/transactions/list";
    }

    @PostMapping("/add-credit")
    public String addCredit(@RequestParam("memberId") Long memberId,
                            @RequestParam("amount") Integer amount,
                            @RequestParam("note") String note,
                            RedirectAttributes redirectAttributes) {
        try {
            creditService.addCredits(memberId, amount, note);
            redirectAttributes.addFlashAttribute("success", "Đã nạp thành công " + amount + " buổi tập!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi nạp buổi tập: " + e.getMessage());
        }

        return "redirect:/admin/transactions";
    }
    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {
        try {
            creditService.approveTransaction(id);
            ra.addFlashAttribute("success", "Phê duyệt nạp buổi thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/transactions";
    }
}