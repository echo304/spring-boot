package com.sangboak.webapp.service;


import com.sangboak.core.entity.Account;
import com.sangboak.core.entity.Board;
import com.sangboak.core.entity.Post;
import com.sangboak.core.repository.AccountRepository;
import com.sangboak.core.repository.BoardRepository;
import com.sangboak.core.repository.PostRepository;
import com.sangboak.webapp.dto.PageResponseDto;
import com.sangboak.webapp.dto.PostDetailResponseDto;
import com.sangboak.webapp.dto.PostListResponseDto;
import com.sangboak.webapp.dto.PostSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostService {
    final private static int MAXIMUM_POSTS_PER_DAY = 5;
    final private static int POST_PER_PAGE = 3;

    private PostRepository postRepository;
    private BoardRepository boardRepository;
    private AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public PageResponseDto<PostListResponseDto> getPostsByBoardId(Long boardId, Integer pageNum) {
        Page<Post> page = postRepository.findByBoardId(boardId, PageRequest.of(
                pageNum - 1, POST_PER_PAGE,
                Sort.by(Sort.Direction.DESC, "createdDate")));

        List<PostListResponseDto> dtoList = page.getContent()
                .stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());

        return PageResponseDto.<PostListResponseDto>builder()
                .data(dtoList)
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }

    @Transactional(readOnly = true)
    public PostDetailResponseDto getPost(Long id) throws Exception {
        Post post = postRepository.findById(id).orElseThrow();
        return new PostDetailResponseDto(post);
    }

    @Transactional
    public boolean isMaximumPostsPerDayReached(Long accountId) {
        int postCountOfToday = postRepository.countByCreatedDateBetweenAndOwnedBy(
                LocalDate.now().atStartOfDay(),
                LocalDate.now().atTime(LocalTime.MAX),
                accountId);

        return postCountOfToday >= MAXIMUM_POSTS_PER_DAY;
    }

    @Transactional
    public Long savePost(PostSaveRequestDto dto) throws Exception {
        Account account = accountRepository.findByEmail(dto.getAuthorEmail()).orElseThrow();
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow();

        return postRepository.save(Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(account)
                .board(board)
                .build()).getId();
    }

    @Transactional
    public Long savePost(Long id, PostSaveRequestDto dto) throws Exception {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        return postRepository.save(post).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
