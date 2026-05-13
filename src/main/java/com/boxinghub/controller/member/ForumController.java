package com.boxinghub.controller.member;

import com.boxinghub.entity.Post;
import com.boxinghub.service.MemberService;
import com.boxinghub.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member/forum")
@RequiredArgsConstructor
public class ForumController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping
    public String showForum(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String currentEmail = (userDetails != null) ? userDetails.getUsername() : null;

        if (currentEmail != null) {
            memberService.getMemberByEmail(currentEmail).ifPresent(member -> {
                model.addAttribute("currentMember", member);
            });
        }

        model.addAttribute("posts", postService.getAllPosts(currentEmail));
        model.addAttribute("activePage", "forum");
        model.addAttribute("pageTitle", "Diễn đàn BoxingHub");

        return "member/forum/list";
    }

    @PostMapping("/create")
    public String createPost(@RequestParam String title,
                             @RequestParam String content,
                             @RequestParam("file") MultipartFile file,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        try {
            postService.createPost(title, content, userDetails.getUsername(), file);
            redirectAttributes.addFlashAttribute("success", "Đăng bài thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/member/forum";
    }

    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        try {
            postService.deletePost(id, userDetails.getUsername());
            redirectAttributes.addFlashAttribute("success", "Xóa bài viết thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/member/forum";
    }

    @PostMapping("/like/{id}")
    @ResponseBody
    public void likePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        postService.toggleLike(id, userDetails.getUsername());
    }

    @PostMapping("/comment/{id}")
    @ResponseBody
    public ResponseEntity<?> addComment(@PathVariable("id") Long postId,
                                        @RequestParam("content") String content,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        try {
            var comment = postService.addComment(postId, content, userDetails.getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("content", comment.getContent());
            response.put("authorName", comment.getAuthor().getFullName());
            response.put("authorAvatar", comment.getAuthor().getAvatarUrl());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/post/{id}")
    public String showPostDetail(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String currentEmail = (userDetails != null) ? userDetails.getUsername() : null;

        if (currentEmail != null) {
            memberService.getMemberByEmail(currentEmail).ifPresent(member -> {
                model.addAttribute("currentMember", member);
            });
        }

        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("pageTitle", "Chi tiết bài viết");
        model.addAttribute("activePage", "forum");
        return "member/forum/detail";
    }

    @PostMapping("/report")
    @ResponseBody
    public ResponseEntity<?> reportContent(@RequestParam Long targetId,
                                           @RequestParam String type,
                                           @RequestParam String reason,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        try {
            postService.reportContent(targetId, type, reason, userDetails.getUsername());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}