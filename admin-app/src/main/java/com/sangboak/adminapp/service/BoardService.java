package com.sangboak.adminapp.service;

import com.sangboak.adminapp.dto.BoardListResponseDto;
import com.sangboak.adminapp.dto.BoardDto;
import com.sangboak.core.entity.Board;
import com.sangboak.core.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardService {
    final private BoardRepository boardRepository;

    @Transactional
    public List<BoardListResponseDto> getAll() {
        return boardRepository.findAll()
                .stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveBoard(BoardDto dto) {
        Board board = Board.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public void saveBoard(Long id, BoardDto dto) {
        Board board = boardRepository.findById(id).orElseThrow();

        board.setName(dto.getName());
        board.setDescription(dto.getDescription());

        boardRepository.save(board);
    }

    @Transactional
    public BoardDto findById(Long id) throws Exception {
        Board board = boardRepository.findById(id).orElseThrow();
        return BoardDto.builder()
                .name(board.getName())
                .description(board.getDescription())
                .build();
    }

    @Transactional
    public void deleteBoard(Long id) { boardRepository.deleteById(id); }
}
