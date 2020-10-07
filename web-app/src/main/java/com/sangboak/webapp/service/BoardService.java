package com.sangboak.webapp.service;

import com.sangboak.core.repository.BoardRepository;
import com.sangboak.webapp.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;

    public List<BoardDto> getBoardList() {
        return boardRepository.findAll().stream().map(BoardDto::new).collect(Collectors.toList());
    }
}
