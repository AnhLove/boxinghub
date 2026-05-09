package com.boxinghub.service;

import com.boxinghub.entity.Member;
import com.boxinghub.entity.Post;
import com.boxinghub.repository.MemberRepository;
import com.boxinghub.repository.PostRepository;
import com.boxinghub.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public Post createPost(String title, String content, String userEmail, MultipartFile file) throws IOException {
        Member member = memberRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Member not found with email: " + userEmail));

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(member);
        post.setCreatedAt(LocalDateTime.now());
        post.setLikes(0);
        post.setHidden(false);

        if (file != null && !file.isEmpty()) {
            String projectRoot = System.getProperty("user.dir");

            File subFolder = new File(projectRoot, "boxinghub");
            if (subFolder.exists() && subFolder.isDirectory()) {
                projectRoot = subFolder.getAbsolutePath();
            }

            String uploadDir = projectRoot + File.separator + "src" + File.separator + "main" +
                    File.separator + "resources" + File.separator + "static" +
                    File.separator + "uploads" + File.separator + "posts";

            String fileName = FileUploadUtil.saveFile(uploadDir, file);
            post.setMediaUrl("/uploads/posts/" + fileName);

            String contentType = file.getContentType();
            post.setMediaType(contentType != null && contentType.startsWith("video") ? "VIDEO" : "IMAGE");
        }

        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id, String userEmail) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // SECURITY CHECK: Only author can delete
        if (!post.getAuthor().getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("You are not authorized to delete this post!");
        }

        postRepository.delete(post);
    }

    @Override
    public void toggleLike(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setLikes((post.getLikes() == null ? 0 : post.getLikes()) + 1);
        postRepository.save(post);
    }
}