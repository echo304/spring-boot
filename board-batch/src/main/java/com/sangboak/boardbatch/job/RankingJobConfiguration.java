package com.sangboak.boardbatch.job;

import com.sangboak.core.entity.Account;
import com.sangboak.core.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Log
@RequiredArgsConstructor
@Configuration
public class RankingJobConfiguration {
    final private JobBuilderFactory jobBuilderFactory;
    final private StepBuilderFactory stepBuilderFactory;
    final private AccountRepository accountRepository;

    @Bean
    public Job calculateRanking() {
        log.info("********** This is calculateRankingJob");
        return jobBuilderFactory.get("calculateRankingJob")  // 1_1
                .preventRestart()  // 1_2
                .start(calculateRankingJobStep())  // 1_3
                .build();  // 1_4
    }

    @Bean
    public Step calculateRankingJobStep() {
        log.info("********** This is calculateRankingJobStep");
        return stepBuilderFactory.get("calculateRankingJobStep")  // 2_1
                .<Account, Account> chunk(10)  // 2_2
                .reader(accountReader())  // 2_3
                .processor(scoreCalculator())  // 2_4
                .writer()  // 2_5
                .build();  // 2_6
    }

    @Bean
    @StepScope  // 1
    public ListItemReader<Account> accountReader() {
        log.info("********** This is accountReader");
        List<Account> accounts = accountRepository.findAll();
        log.info("          - activeMember SIZE : " + accounts.size());  // 2
        return new ListItemReader<>(accounts);  // 3
    }

    public ItemProcessor<Account, Account> scoreCalculator() {
        return new ItemProcessor<Account, Account>() {  // 1
            @Override
            public Account process(Account account) throws Exception {
                log.info("********** This is unPaidMemberProcessor");
                return account.setStatusByUnPaid();  // 2
            }
        };
    }
}