package com.sangboak.core.repository;

import com.sangboak.core.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT COUNT(id) FROM posts WHERE created_date BETWEEN ?1 AND ?2 AND author_id = ?3 AND deleted = false")
    int countByCreatedDateBetweenAndOwnedBy(LocalDateTime from, LocalDateTime to, Long authorId);

    List<Post> findByBoardId(Long id);
    Page<Post> findByBoardId(Long id, Pageable pageable);
}
