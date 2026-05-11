package com.boxinghub.service;

import com.boxinghub.entity.Comment;
import com.boxinghub.entity.Member;
import com.boxinghub.entity.Post;
import com.boxinghub.entity.PostLike;
import com.boxinghub.repository.CommentRepository;
import com.boxinghub.repository.MemberRepository;
import com.boxinghub.repository.PostLikeRepository;
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
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Override
    public List<Post> getAllPosts(String currentUserEmail) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        // Nếu người dùng đã đăng nhập, kiểm tra trạng thái like cho từng bài
        if (currentUserEmail != null) {
            Optional<Member> memberOpt = memberRepository.findByUserEmail(currentUserEmail);
            if (memberOpt.isPresent()) {
                Member member = memberOpt.get();
                posts.forEach(post -> {
                    // Kiểm tra sự tồn tại trong bảng PostLike
                    boolean isLiked = postLikeRepository.findByPostAndMember(post, member).isPresent();
                    post.setLikedByCurrentUser(isLiked);
                });
            }
        }
        return posts;
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
    @Transactional
    public void toggleLike(Long postId, String userEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Member member = memberRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Optional<PostLike> existingLike = postLikeRepository.findByPostAndMember(post, member);

        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
            post.setLikes(Math.max(0, (post.getLikes() == null ? 0 : post.getLikes()) - 1));
        } else {
            PostLike newLike = new PostLike();
            newLike.setPost(post);
            newLike.setMember(member);
            postLikeRepository.save(newLike);
            post.setLikes((post.getLikes() == null ? 0 : post.getLikes()) + 1);
        }

        postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional
    public Comment addComment(Long postId, String content, String userEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết"));

        Member member = memberRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin thành viên"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setAuthor(member);

        return commentRepository.save(comment);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + id));
    }
}