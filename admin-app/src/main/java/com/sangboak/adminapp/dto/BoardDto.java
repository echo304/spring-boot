package com.sangboak.adminapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class BoardDto {

    @NotBlank(message = "필수 입력 값입니다.")
    @Length(max = 100)
    private String name;

    @NotBlank(message = "필수 입력 값입니다.")
    @Length(max = 500)
    private String description;

    @Builder
    public BoardDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
