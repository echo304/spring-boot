package com.sangboak.adminapp.scheduler;

import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Log
@Service
public class BatchScheduler {

    final static private String BATCH_APP_URL = "http://localhost:8082/calculate-ranking";

    @Scheduled(cron = "0 0 1 * * ?")
    public void triggerCalculateRanking() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(BATCH_APP_URL)
                .queryParam("t", LocalDateTime.now().toString())
                .build(false);

        restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class);
    }
}
