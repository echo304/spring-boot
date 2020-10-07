package com.sangboak.webapp.controller;

import com.sangboak.webapp.config.CustomSecurityUser;
import com.sangboak.webapp.dto.ReplyResponseDto;
import com.sangboak.webapp.dto.ReplySaveRequestDto;
import com.sangboak.webapp.service.MailService;
import com.sangboak.webapp.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log
@Controller
@AllArgsConstructor
@RequestMapping("/boards/{boardId}/posts/{postId}/replies")
public class ReplyController {

    final private ReplyService replyService;
    final private MailService mailService;

    @PostMapping
    public String createReply(
            @PathVariable("boardId") Long boardId,
            @PathVariable("postId") Long postId,
            @Valid ReplySaveRequestDto dto,
            @AuthenticationPrincipal CustomSecurityUser authUser
    ) {
        dto.setPostId(postId);
        Long authUserId = authUser.getAccount().getId();
        replyService.saveReplyOf(authUserId, dto);
        mailService.sendReplyNotiMail(authUserId, postId);
        return String.format("redirect:/boards/%s/posts/%s", boardId, postId);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ReplyResponseDto updateReply(
            @PathVariable("id") Long id,
            @Valid ReplySaveRequestDto dto
    ) {
        return replyService.updateReply(id, dto);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public void deleteReply(@PathVariable("id") Long id) {
        replyService.deleteReply(id);
    }
}
