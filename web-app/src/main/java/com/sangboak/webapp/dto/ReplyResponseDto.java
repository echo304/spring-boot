package com.sangboak.webapp.dto;

import com.sangboak.core.entity.Reply;
import lombok.Getter;

@Getter
public class ReplyResponseDto extends BaseTimeResponseDto {
    private Long id;
    private String content;
    private String authorEmail;
    private String authorRank;
    private String modifiedDate;

    public ReplyResponseDto(Reply entity) {
        id = entity.getId();
        content = entity.getContent();
        authorEmail = entity.getAuthor().getEmail();
        if (entity.getAuthor().getRank().isEmpty()) {
            authorRank = "랭크없음";
        } else {
            authorRank = entity.getAuthor().getRank().get().toString() + "등!!";
        }
        modifiedDate = toStringDateTime(entity.getModifiedDate());
    }
}
