package com.boxinghub.controller.member;

import com.boxinghub.entity.Post;
import com.boxinghub.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member/forum")
public class ForumController {

    @Autowired
    private PostService postService;

    // Hiển thị danh sách bài viết
    @GetMapping
    public String showForum(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        model.addAttribute("activePage", "forum");
        model.addAttribute("pageTitle", "Diễn đàn BoxingHub");
        return "member/forum/list";
    }

    // Xử lý đăng bài mới
    @PostMapping("/create")
    public String createPost(@RequestParam String title,
                             @RequestParam String content,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        try {
            // Gọi Service xử lý để đảm bảo tính đóng gói
            postService.createPost(title, content, userDetails.getUsername());
            redirectAttributes.addFlashAttribute("success", "Đăng bài thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi đăng bài.");
        }
        return "redirect:/member/forum";
    }
}