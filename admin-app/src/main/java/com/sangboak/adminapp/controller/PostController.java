package com.sangboak.adminapp.controller;

import com.sangboak.adminapp.dto.PostListResponseDto;
import com.sangboak.adminapp.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/boards/{boardId}")
@AllArgsConstructor
public class PostController {
    final private PostService postService;

    @GetMapping("/posts")
    public String listPage(
            @PathVariable("boardId") Long boardId,
            Model model
    ) {
        List<PostListResponseDto> postList = postService.getPostsByBoardId(boardId);

        model.addAttribute("posts", postList);

        return "post/index";
    }
}
