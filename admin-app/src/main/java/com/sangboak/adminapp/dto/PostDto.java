package com.sangboak.adminapp.dto;

import com.sangboak.core.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String authorEmail;
    private LocalDateTime modifiedDate;

    public  PostDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.authorEmail = entity.getAuthor().getEmail();
        this.modifiedDate = entity.getModifiedDate();
    }
}
