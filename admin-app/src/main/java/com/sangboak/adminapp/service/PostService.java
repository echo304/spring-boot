package com.sangboak.adminapp.service;

import com.sangboak.adminapp.dto.PostDto;
import com.sangboak.adminapp.dto.PostListResponseDto;
import com.sangboak.core.entity.Post;
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

    @Transactional(readOnly = true)
    public List<PostListResponseDto> getPostsByBoardId(Long id) {
        return postRepository.findByBoardId(id).stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDto getPost(Long id) throws Exception {
        Post post = postRepository.findById(id).orElseThrow();
        return new PostDto(post);
    }

    @Transactional
    public void deletePost(Long id) throws Exception {
        postRepository.deleteById(id);
    }
}
