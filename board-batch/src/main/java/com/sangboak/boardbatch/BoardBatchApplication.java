package com.sangboak.boardbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableBatchProcessing
@SpringBootApplication
@PropertySource(value = {"classpath:/data-access.properties"})
@EntityScan("com.sangboak.*")
@EnableJpaRepositories("com.sangboak.*")
public class BoardBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardBatchApplication.class, args);
	}

}
