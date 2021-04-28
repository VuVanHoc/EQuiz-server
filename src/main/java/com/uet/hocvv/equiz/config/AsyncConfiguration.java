package com.uet.hocvv.equiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration {
	
	@Bean(name = "taskExecutor")
	public Executor taskExecutor() {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setMaxPoolSize(3);
		executor.setCorePoolSize(3);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("Task Executor Thread - ");
		executor.initialize();
		return executor;
	}
}
