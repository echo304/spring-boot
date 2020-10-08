package com.sangboak.webapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PageResponseDto<T> {
    private List<T> data;
    private boolean hasNext;
    private boolean hasPrevious;

    @Builder
    public PageResponseDto (List<T> data, boolean hasNext, boolean hasPrevious) {
        this.data = data;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }
}
