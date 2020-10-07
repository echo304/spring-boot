package com.sangboak.webapp.controller;

import com.sangboak.webapp.config.CustomSecurityUser;
import com.sangboak.webapp.dto.ReplySaveRequestDto;
import com.sangboak.webapp.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Log
@Controller
@AllArgsConstructor
@RequestMapping("/boards/{boardId}/posts/{postId}/replies")
public class ReplyController {

    final private ReplyService replyService;

    @PostMapping
    public String createReply(
            @PathVariable("boardId") Long boardId,
            @PathVariable("postId") Long postId,
            @Valid ReplySaveRequestDto dto,
            @AuthenticationPrincipal CustomSecurityUser authUser
    ) {
        dto.setPostId(postId);
        replyService.saveReplyOf(authUser.getAccount().getId(), dto);
        return String.format("redirect:/boards/%s/posts/%s", boardId, postId);
    }
}
