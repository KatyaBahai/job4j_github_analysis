package ru.job4j.github.analysis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
    @Bean
    public ThreadPoolTaskExecutor initExecutorPool() {
        var pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(2);
        return pool;
    }

}
