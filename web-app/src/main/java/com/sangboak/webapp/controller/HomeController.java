package com.sangboak.webapp.controller;

import com.sangboak.webapp.dto.BoardDto;
import com.sangboak.webapp.dto.HackerNewsStoryDto;
import com.sangboak.webapp.service.BoardService;
import com.sangboak.webapp.service.HackerNewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {
    private static final int NEW_STORIES_COUNT = 10;

    private BoardService boardService;
    private HackerNewsService hackerNewsService;

    @GetMapping("/")
    public String index(Model model) {
        List<BoardDto> boards = boardService.getBoardList();
        List<HackerNewsStoryDto> stories = hackerNewsService.getNewStories(NEW_STORIES_COUNT);

        model.addAttribute("stories", stories);
        model.addAttribute("boards", boards);
        return "index";
    }
}