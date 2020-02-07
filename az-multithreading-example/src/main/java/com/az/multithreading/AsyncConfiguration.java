package com.az.multithreading;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

	@Bean
	public Executor taskExecutor() {		
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();		
		threadPoolTaskExecutor.setCorePoolSize(2);
		threadPoolTaskExecutor.setMaxPoolSize(2);
		threadPoolTaskExecutor.setQueueCapacity(10);
		threadPoolTaskExecutor.setThreadNamePrefix("AzTaskExecutor-");
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
	}
}
