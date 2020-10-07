package com.sangboak.core.repository;

import com.sangboak.core.entity.Post;
import com.sangboak.core.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByPostId(Long postId);
}
