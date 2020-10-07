package com.sangboak.webapp.dto;

import com.sangboak.core.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostDetailResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String authorEmail;
    private LocalDateTime modifiedDate;

    public  PostDetailResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.authorId = entity.getAuthor().getId();
        this.authorEmail = entity.getAuthor().getEmail();
        this.modifiedDate = entity.getModifiedDate();
    }
}
