package com.boxinghub.service;

import com.boxinghub.entity.Comment;
import com.boxinghub.entity.Post;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface PostService {
    List<Post> getAllPosts(String currentUserEmail);

    Post getPostById(Long id);

    Post createPost(String title, String content, String userEmail, MultipartFile file) throws IOException;

    void deletePost(Long id, String userEmail);

    void toggleLike(Long postId, String userEmail);

    Comment addComment(Long postId, String content, String userEmail);
}