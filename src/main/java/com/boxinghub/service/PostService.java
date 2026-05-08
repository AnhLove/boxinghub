package com.boxinghub.service;

import com.boxinghub.entity.Post;
import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post createPost(String title, String content, String userEmail);
    void deletePost(Long id);
}