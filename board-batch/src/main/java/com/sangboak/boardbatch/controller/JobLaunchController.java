package com.sangboak.boardbatch.controller;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@AllArgsConstructor
@RestController

public class JobLaunchController {

    final private WebApplicationContext context;
    final private JobLauncher jobLauncher;

    @GetMapping("/calculate-ranking")
    public void handle() throws Exception{
        jobLauncher.run(getJob("calculateRanking"), new JobParameters());
    }

    private Job getJob(String jobName) {
        return (Job) context.getBean(jobName);
    }
}
