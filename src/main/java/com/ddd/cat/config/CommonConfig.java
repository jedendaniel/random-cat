package com.ddd.cat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

import java.util.Random;

@Configuration
public class CommonConfig {

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
