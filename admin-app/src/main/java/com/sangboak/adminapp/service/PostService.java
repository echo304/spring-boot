package com.sangboak.adminapp.service;

import com.sangboak.adminapp.dto.PostListResponseDto;
import com.sangboak.core.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostService {

    final private PostRepository postRepository;

    @Transactional
    public List<PostListResponseDto> getPostsByBoardId(Long id) {
        return postRepository.findByBoardId(id).stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }
}
