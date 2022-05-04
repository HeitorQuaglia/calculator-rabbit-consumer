package edu.quaglia.calculatorrabbitconsumer.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private final Logger LOGGER = LoggerFactory.getLogger(AsyncConfig.class);

    @Bean("reentrantLock")
    public ReentrantLock reentrantLock() {
        return new ReentrantLock();
    }

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        LOGGER.info("Creating taskExecutor bean... ");

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setTaskDecorator(new MdcTaskDecorator());
        executor.setCorePoolSize(1);
        executor.setThreadNamePrefix("async-");
        executor.initialize();

        return executor;
    }
}
