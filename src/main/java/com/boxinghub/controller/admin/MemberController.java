package com.boxinghub.controller.admin;

import com.boxinghub.entity.Member;
import com.boxinghub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // Hiển thị danh sách member
    @GetMapping
    public String listMembers(@RequestParam(value = "keyword", required = false) String keyword,
                              Model model) {
        List<Member> members = memberService.searchByName(keyword);

        model.addAttribute("members", members);
        model.addAttribute("keyword", keyword);
        return "admin/members/list";
    }

    // Hiển thị form thêm mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("member", new Member());
        return "admin/members/form";
    }

    // Xử lý thêm/sửa member
    @PostMapping("/save")
    public String saveMember(@ModelAttribute Member member) {
        memberService.saveMember(member);
        return "redirect:/admin/members";
    }

    // Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        memberService.getMemberById(id).ifPresent(
                member -> model.addAttribute("member", member)
        );
        return "admin/members/form";
    }

    // Xóa member
    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return "redirect:/admin/members";
    }
}