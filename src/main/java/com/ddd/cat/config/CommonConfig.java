package com.ddd.cat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.Random;

@Configuration
public class CommonConfig {

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
