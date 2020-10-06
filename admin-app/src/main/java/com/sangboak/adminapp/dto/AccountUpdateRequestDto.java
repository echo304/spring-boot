package com.sangboak.adminapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class AccountUpdateRequestDto {
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

//    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
//    	message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    private boolean resetPassword;

    private boolean admin;

    @Builder
    public AccountUpdateRequestDto(
            String name,
            String password,
            boolean resetPassword,
            boolean admin
    ) {
        this.name = name;
        this.password = password;
        this.resetPassword = resetPassword;
        this.admin = admin;
    }
}
