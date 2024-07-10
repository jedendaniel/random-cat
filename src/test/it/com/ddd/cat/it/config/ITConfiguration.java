package com.ddd.cat.it.config;

import com.ddd.cat.mock.S3ServerMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.s3.S3Client;

@TestConfiguration
public class ITConfiguration {
    @Bean
    public S3Client s3Client() {
        return new S3ServerMock();
    }
}
