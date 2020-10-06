package com.sangboak.adminapp.dto;

import com.sangboak.core.entity.Account;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccountListResponseDto {
    private Long id;
    private String email;
    private String name;
    private boolean deleted;
    private LocalDateTime createdDate;

    public AccountListResponseDto(Account entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.deleted = entity.isDeleted();
        this.createdDate = entity.getCreatedDate();
    }
}
