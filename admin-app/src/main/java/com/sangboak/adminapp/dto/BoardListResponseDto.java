package com.sangboak.adminapp.dto;

import com.sangboak.core.entity.Board;
import lombok.Getter;

@Getter
public class BoardListResponseDto {
    private Long id;
    private String name;
    private String description;
    private boolean deleted;

    public  BoardListResponseDto(Board entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.deleted = entity.isDeleted();
    }
}
