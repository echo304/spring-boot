package com.sangboak.webapp.dto;

import com.sangboak.core.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String name;
    private String description;

    public BoardDto(Board entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
    }
}

