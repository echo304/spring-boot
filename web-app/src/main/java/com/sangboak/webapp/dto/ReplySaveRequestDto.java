package com.sangboak.webapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class ReplySaveRequestDto {
    @NotBlank(message = "댓글 내용은 필수항목입니다.")
    private String content;
    private Long postId;
}
