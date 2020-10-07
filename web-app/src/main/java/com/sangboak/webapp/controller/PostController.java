package com.sangboak.webapp.controller;

import com.sangboak.webapp.config.CustomSecurityUser;
import com.sangboak.webapp.dto.PostDetailResponseDto;
import com.sangboak.webapp.dto.PostListResponseDto;
import com.sangboak.webapp.dto.PostSaveRequestDto;
import com.sangboak.webapp.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/boards/{boardId}/posts")
public class PostController {

    private PostService postService;

    @GetMapping
    public String list(@PathVariable("boardId") Long boardId, Model model, Authentication authentication) {
        List<PostListResponseDto> postList = postService.getPostsByBoardId(boardId);

        model.addAttribute("posts", postList);
        return "post/index";
    }

    @GetMapping("/{id}")
    public String detail(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            Model model,
            @AuthenticationPrincipal CustomSecurityUser authUser
        ) {
        PostDetailResponseDto postDto;
        try {
            postDto = postService.getPost(id);
        }  catch (Exception e) {
            log.warning("Exception occured!: " + e.getMessage());
            return "redirect:/boards/" + boardId + "/posts";
        }

        if (authUser != null) {
            model.addAttribute("account", authUser.getAccount());
        }

        model.addAttribute("postDto", postDto);
        return "post/detail";
    }

    @GetMapping("/create")
    public String write(
            @PathVariable("boardId") Long boardId,
            @ModelAttribute("postDto") PostSaveRequestDto dto
    ) {
        return "post/write";
    }

    @PostMapping
    public String savePost(
            @PathVariable("boardId") Long boardId,
            @AuthenticationPrincipal CustomSecurityUser authUser,
            @Valid PostSaveRequestDto dto,
            Errors errors,
            Model model
    ) {
        if (errors.hasErrors()) {
            model.addAttribute("postDto", dto);

            for (FieldError error: errors.getFieldErrors()) {
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }

            return "/post/write";
        }

        boolean maximumPostsPerDayReached =
                postService.isMaximumPostsPerDayReached(authUser.getAccount().getId());

        if (maximumPostsPerDayReached) {
            model.addAttribute("postDto", dto);
            model.addAttribute("genericError", "게시글을 하루에 5개 초과로 작성할 수 없습니다.");

            return "/post/write";
        }

        dto.setAuthorEmail(authUser.getUsername());
        dto.setBoardId(boardId);

        try {
            postService.savePost(dto);
        } catch (Exception e) {
            log.warning("Exception occured!: " + e.getMessage());
            return "redirect:/boards/" + boardId + "/posts";
        }

        return String.format("redirect:/boards/%s/posts", boardId);
    }

    @GetMapping("/{id}/edit")
    public String edit(
            @PathVariable("id") Long id,
            @PathVariable("boardId") Long boardId,
            @ModelAttribute("postDto") PostSaveRequestDto dto,
            Model model
    ) {
        PostDetailResponseDto postDto;
        try {
            postDto = postService.getPost(id);
        } catch (Exception e) {
            log.warning("Exception occured!: " + e.getMessage());
            return "redirect:/boards/" + boardId + "/posts";
        }

        model.addAttribute("postDto", postDto);
        return "post/update";
    }

    @PutMapping("/{id}")
    public String update(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            @Valid PostSaveRequestDto dto,
            Errors errors,
            Model model
    ) {
        if (errors.hasErrors()) {
            model.addAttribute("postDto", dto);

            for (FieldError error: errors.getFieldErrors()) {
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }

            return "/post/update";
        }

        try {
            postService.savePost(id, dto);
        } catch (Exception e) {
            log.warning("Exception occured!: " + e.getMessage());
            return String.format("redirect:/boards/%s/posts/%s", boardId, id);
        }

        return String.format("redirect:/boards/%s/posts/%s", boardId, id);
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id
    ) {
        postService.deletePost(id);
        return String.format("redirect:/boards/%s/posts", boardId);
    }
}
