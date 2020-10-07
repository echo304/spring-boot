package com.sangboak.adminapp.controller;

import com.sangboak.adminapp.dto.BoardListResponseDto;
import com.sangboak.adminapp.dto.BoardDto;
import com.sangboak.adminapp.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log
@Controller
@AllArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    final private BoardService boardService;

    @GetMapping
    public String listPage(Model model) {
        List<BoardListResponseDto> boardList = boardService.getAll();
        model.addAttribute("boards", boardList);

        return "board/index";
    }

    @GetMapping("/create")
    public String createPage(@ModelAttribute("boardDto") BoardDto dto) {
        return "board/create";
    }

    @PostMapping("/create")
    public String create(
            Model model,
            @Valid BoardDto dto,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            model.addAttribute("boardDto", dto);

            for (FieldError error: errors.getFieldErrors()) {
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }

            return "board/create";
        }

        boardService.saveBoard(dto);

        return "redirect:/boards";
    }

    @GetMapping("/{id}/edit")
    public String editPage(
            @PathVariable("id") Long id,
            Model model
    ) {
        BoardDto dto;
        try {
            dto = boardService.findById(id);
        } catch (Exception e) {
            log.warning(("Exception occured!: " + e.getMessage()));
            return "redirect:/boards";
        }

        model.addAttribute("boardDto", dto);
        return "board/edit";
    }

    @PutMapping("/{id}")
    public String edit(
            @Valid BoardDto dto,
            @PathVariable("id") Long id,
            Model model,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            model.addAttribute("boardDto", dto);

            for (FieldError error: errors.getFieldErrors()) {
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }

            return "board/edit";
        }

        boardService.saveBoard(id, dto);

        return "redirect:/boards";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);

        return "redirect:/boards";
    }
}
