package com.sangboak.webapp.dto;

import com.sangboak.core.entity.Post;
import lombok.Getter;

@Getter
public class PostListResponseDto extends BaseTimeResponseDto {
    private Long id;
    private String title;
    private String authorEmail;
    private String modifiedDate;

    public PostListResponseDto(Post entity) {
        id = entity.getId();
        title = entity.getTitle();
        authorEmail = entity.getAuthor().getEmail();
        modifiedDate = toStringDateTime(entity.getModifiedDate());
    }
}
