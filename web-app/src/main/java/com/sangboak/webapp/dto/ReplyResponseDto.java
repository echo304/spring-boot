package com.sangboak.webapp.dto;

import com.sangboak.core.entity.Reply;
import lombok.Getter;

@Getter
public class ReplyResponseDto extends BaseTimeResponseDto {
    private Long id;
    private String content;
    private String authorEmail;
    private String modifiedDate;

    public ReplyResponseDto(Reply entity) {
        id = entity.getId();
        content = entity.getContent();
        authorEmail = entity.getAuthor().getEmail();
        modifiedDate = toStringDateTime(entity.getModifiedDate());
    }
}
