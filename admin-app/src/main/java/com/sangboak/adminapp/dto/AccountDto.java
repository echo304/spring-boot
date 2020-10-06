package com.sangboak.adminapp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountDto {
    private String name;
    private String email;
    private boolean admin;
    private boolean deleted;

    @Builder
    public AccountDto(String name, String email, boolean admin, boolean deleted) {
        this.name = name;
        this.email = email;
        this.admin = admin;
        this.deleted = admin;
    }
}
