package com.sangboak.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HackerNewsStoryDto {
    private String by;
    private String id;
    private String title;
    private String url;
}
