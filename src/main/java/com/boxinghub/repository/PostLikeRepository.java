package com.boxinghub.repository;

import com.boxinghub.entity.Member;
import com.boxinghub.entity.Post;
import com.boxinghub.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostAndMember(Post post, Member member);
}