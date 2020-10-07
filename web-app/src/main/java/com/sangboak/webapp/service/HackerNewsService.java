package com.sangboak.webapp.service;

import com.sangboak.webapp.dto.HackerNewsStoryDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class HackerNewsService {
    final private String HACKER_NEWS_API = "https://hacker-news.firebaseio.com/v0";

    public List<HackerNewsStoryDto> getNewStories(int count) {
        WebClient client = WebClient.create(HACKER_NEWS_API);
        String[] newStories = client
                .get()
                .uri("/newstories.json")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String[].class)
                .block();
        String[] tenNewStories = Arrays.copyOfRange(newStories, 0, count);

        return Flux.fromArray(tenNewStories).flatMapSequential(id -> {
            return client.get()
                    .uri("/item/" + id + ".json")
                    .retrieve()
                    .bodyToMono(HackerNewsStoryDto.class);
        }).collectList().block();
    }
}
