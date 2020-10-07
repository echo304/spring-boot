package com.sangboak.adminapp.dto;

import com.sangboak.core.entity.Post;
import lombok.Getter;

@Getter
public class PostListResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String authorEmail;
    private boolean deleted;

    public  PostListResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.authorId = entity.getAuthor().getId();
        this.authorEmail = entity.getAuthor().getEmail();
        this.deleted = entity.isDeleted();
    }
}

