package com.sangboak.webapp.dto;

import com.sangboak.core.validator.AllowedContent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveRequestDto {

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotEmpty
    @AllowedContent(message = "금지어 및 포털사이트로의 링크는 포함될 수 없습니다.")
    private String content;

    private String authorEmail;
    private Long boardId;

    @Builder
    public PostSaveRequestDto(String title, String content, String authorEmail) {
        this.title = title;
        this.content = content;
        this.authorEmail = authorEmail;
    }
}