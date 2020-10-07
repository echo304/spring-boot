package com.sangboak.adminapp.controller;

import com.sangboak.adminapp.dto.PostDto;
import com.sangboak.adminapp.dto.PostListResponseDto;
import com.sangboak.adminapp.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log
@Controller
@RequestMapping("/boards/{boardId}/posts")
@AllArgsConstructor
public class PostController {
    final private PostService postService;

    @GetMapping
    public String listPage(
            @PathVariable("boardId") Long boardId,
            Model model
    ) {
        List<PostListResponseDto> postList = postService.getPostsByBoardId(boardId);

        model.addAttribute("posts", postList);

        return "post/index";
    }

    @GetMapping("/{id}")
    public String detailPage(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            Model model
    ) {
        PostDto postDto;
        try {
            postDto = postService.getPost(id);
        }  catch (Exception e) {
            log.warning("Exception occured!: " + e.getMessage());
            return "redirect:/boards/" + boardId + "/posts";
        }

        model.addAttribute("postDto", postDto);

        return "post/detail";
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id
    ) {
        try {
            postService.deletePost(id);
        }  catch (Exception e) {
            log.warning("Exception occured!: " + e.getMessage());
            return "redirect:/boards/" + boardId + "/posts";
        }

        return "redirect:/boards/" + boardId + "/posts";
    }
}
