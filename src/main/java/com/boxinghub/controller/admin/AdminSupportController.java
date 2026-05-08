package com.boxinghub.controller.admin;

import com.boxinghub.entity.TicketStatus;
import com.boxinghub.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/support")
@RequiredArgsConstructor
public class AdminSupportController {

    private final SupportService supportService;

    @GetMapping
    public String listAllTickets(Model model) {
        model.addAttribute("tickets", supportService.getAllTickets());
        model.addAttribute("activePage", "admin-support");
        model.addAttribute("pageTitle", "Quản lý báo lỗi");
        return "admin/support/support-list";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam("ticketId") Long ticketId,
                               @RequestParam("status") TicketStatus status,
                               RedirectAttributes ra) {
        try {
            supportService.updateTicketStatus(ticketId, status);
            ra.addFlashAttribute("success", "Đã cập nhật trạng thái báo lỗi!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/support";
    }
}