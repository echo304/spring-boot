package com.sangboak.webapp.service;

import com.sangboak.core.entity.Account;
import com.sangboak.core.entity.Post;
import com.sangboak.core.repository.AccountRepository;
import com.sangboak.core.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "noreply@awesome.com";
    final private AccountRepository accountRepository;
    final private PostRepository postRepository;

    public void sendReplyNotiMail(Long repliedAccountId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        Account account = post.getAuthor();
        if (!repliedAccountId.equals(account.getId())) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(account.getEmail());
            message.setFrom(MailService.FROM_ADDRESS);
            message.setSubject("댓글이 등록되었습니다.");
            message.setText("어서 가서 확인해보세요!");

            mailSender.send(message);
        }
    }
}
