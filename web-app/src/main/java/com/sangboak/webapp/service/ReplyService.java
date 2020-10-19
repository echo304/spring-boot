package com.sangboak.webapp.service;

import com.sangboak.core.entity.Account;
import com.sangboak.core.entity.Post;
import com.sangboak.core.entity.Reply;
import com.sangboak.core.repository.AccountRepository;
import com.sangboak.core.repository.PostRepository;
import com.sangboak.core.repository.ReplyRepository;
import com.sangboak.webapp.dto.ReplyResponseDto;
import com.sangboak.webapp.dto.ReplySaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReplyService {
    final private ReplyRepository replyRepository;
    final private AccountRepository accountRepository;
    final private PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<ReplyResponseDto> getRepliesByPostId(Long postId) {
        return replyRepository.findByPostId(postId)
                .stream()
                .map(ReplyResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReplyResponseDto saveReplyOf(Long accountId, ReplySaveRequestDto dto) {
        Account author = accountRepository.getOne(accountId);
        Post post = postRepository.getOne(dto.getPostId());
        Reply reply = Reply.builder()
                .author(author)
                .post(post)
                .content(dto.getContent())
                .build();
        return new ReplyResponseDto(replyRepository.save(reply));
    }

    @Transactional
    @PostAuthorize("returnObject.authorEmail == authentication.name")
    public ReplyResponseDto updateReply(Long id, ReplySaveRequestDto dto) {
        Reply reply = replyRepository.findById(id).orElseThrow();
        reply.setContent(dto.getContent());

        return new ReplyResponseDto(replyRepository.save(reply));
    }

    @Transactional
    public void deleteReply(Long id) {
        replyRepository.deleteById(id);
    }
}
