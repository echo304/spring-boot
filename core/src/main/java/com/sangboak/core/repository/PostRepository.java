package com.sangboak.core.repository;

import com.sangboak.core.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoardId(Long id);
}