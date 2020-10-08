package com.sangboak.boardbatch.controller;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController

public class JobLaunchController {

    final private WebApplicationContext context;
    final private JobLauncher jobLauncher;

    @GetMapping("/calculate-ranking")
    public void handle(@RequestParam("t") String t) throws Exception{
        JobParameter param = new JobParameter(t);
        Map<String, JobParameter> params = new HashMap<String, JobParameter>();
        params.put("timeStamp", param);
        jobLauncher.run(getJob("calculateRanking"), new JobParameters(params));
    }

    private Job getJob(String jobName) {
        return (Job) context.getBean(jobName);
    }
}
