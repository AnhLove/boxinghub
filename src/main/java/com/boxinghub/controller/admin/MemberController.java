package com.boxinghub.controller.admin;

import com.boxinghub.entity.Member;
import com.boxinghub.entity.User; // Cần import thêm User
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

    // HIỂN THỊ FORM THÊM MỚI (ĐÃ SỬA)
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Member member = new Member();
        member.setUser(new User()); // QUAN TRỌNG: Phải khởi tạo User rỗng để form map được field email
        model.addAttribute("member", member);
        return "admin/members/form";
    }

    // XỬ LÝ THÊM/SỬA MEMBER
    @PostMapping("/save")
    public String saveMember(@ModelAttribute Member member) {
        // Logic mã hóa pass và tạo User đã nằm trong MemberServiceImpl.saveMember
        memberService.saveMember(member);
        return "redirect:/admin/members";
    }

    // HIỂN THỊ FORM SỬA (ĐÃ SỬA ĐỂ AN TOÀN HƠN)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        memberService.getMemberById(id).ifPresentOrElse(
                member -> {
                    // Đảm bảo nếu member cũ chưa có user thì khởi tạo để không lỗi form
                    if (member.getUser() == null) {
                        member.setUser(new User());
                    }
                    model.addAttribute("member", member);
                },
                () -> { /* Có thể thêm logic xử lý nếu không tìm thấy id */ }
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