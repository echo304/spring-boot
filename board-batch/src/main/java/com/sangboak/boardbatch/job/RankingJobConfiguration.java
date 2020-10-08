package com.sangboak.boardbatch.job;

import com.sangboak.boardbatch.dto.ScoreDto;
import com.sangboak.core.entity.Ranking;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Log
@RequiredArgsConstructor
@Configuration
public class RankingJobConfiguration {
    final private JobBuilderFactory jobBuilderFactory;
    final private StepBuilderFactory stepBuilderFactory;
    final private EntityManagerFactory entityManagerFactory;
    final private DataSource dataSource;

    @Bean
    public Job calculateRanking() {
        return jobBuilderFactory.get("calculateRankingJob")
                .start(calculateRankingScoreStep())
                .next(calculateRankStep())
                .build();
    }

    @Bean
    public Step calculateRankingScoreStep() {
        return stepBuilderFactory.get("calculateRankingScoreStep")
                .<ScoreDto, ScoreDto> chunk(10)
                .reader(scoreReader())
                .writer(scoreWriter())
                .build();
    }

    @Bean
    public Step calculateRankStep() {
        return stepBuilderFactory.get("calculateRankStep")
                .<Ranking, Ranking> chunk(10)
                .reader(rankingReader())
                .writer(rankingWriter())
                .build();
    }

    @Bean
    public ItemReader<ScoreDto> scoreReader() {
        String sql =
                        "SELECT DISTINCT\n" +
                        "   account.id as id,\n" +
                        "   (SELECT COUNT(*) * 10 FROM posts ps WHERE Date(ps.created_date) > CURDATE() - INTERVAL 30 DAY and ps.deleted = 0 AND ps.author_id = account.id) AS post_score,\n" +
                        "   (SELECT COUNT(*) * 5 FROM replies r WHERE Date(r.created_date) > CURDATE() - INTERVAL 30 DAY and r.deleted = 0 AND r.author_id = account.id) AS reply_score,\n" +
                        "   (SELECT post_score) + (SELECT reply_score) as score\n" +
                        "FROM accounts account\n" +
                        "   INNER JOIN posts ps_ ON ps_.author_id = account.id \n" +
                        "   INNER JOIN boards bs_ ON ps_.board_id = bs_.id";

        return new JdbcCursorItemReaderBuilder<ScoreDto>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql(sql)
                .rowMapper(new BeanPropertyRowMapper<>(ScoreDto.class))
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<ScoreDto> scoreWriter() {
        return new JdbcBatchItemWriterBuilder<ScoreDto>()
                .dataSource(dataSource)
                .sql("insert into rankings(id, score) values (:id, :score) " +
                        "ON DUPLICATE KEY UPDATE score = :score")
                .beanMapped()
                .build();
    }

    @Bean
    public ItemReader<Ranking> rankingReader() {
        String sql =
                "SELECT id, score, @rank := @rank + 1 rank\n" +
                "FROM rankings, (SELECT @rank := 0) init\n" +
                "ORDER BY score DESC";

        return new JdbcCursorItemReaderBuilder<Ranking>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql(sql)
                .rowMapper(new BeanPropertyRowMapper<>(Ranking.class))
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Ranking> rankingWriter() {
        return new JdbcBatchItemWriterBuilder<Ranking>()
                .dataSource(dataSource)
                .sql("update rankings set rank = :rank where id = :id")
                .beanMapped()
                .build();
    }
}