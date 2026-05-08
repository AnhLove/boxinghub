package com.boxinghub.service;

import com.boxinghub.entity.Member;
import com.boxinghub.entity.Post;
import com.boxinghub.repository.MemberRepository;
import com.boxinghub.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Post createPost(String title, String content, String userEmail) {
        // Sửa lỗi: Lấy Member từ Optional, nếu không thấy thì trả về null hoặc báo lỗi
        Member member = memberRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Member not found with email: " + userEmail));

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(member);
        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}