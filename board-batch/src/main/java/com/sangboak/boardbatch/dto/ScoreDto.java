package com.sangboak.boardbatch.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ScoreDto {
    private Long id;
    private int postScore;
    private int replyScore;
    private int score;
}
