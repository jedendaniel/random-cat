package com.ddd.cat.it.config;

import com.ddd.cat.mock.S3ServerMock;
import com.ddd.cat.properties.AwsProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import software.amazon.awssdk.services.s3.S3Client;

@TestConfiguration
@ContextConfiguration(classes = {AwsProperties.class, S3ServerMock.class})
public class ITConfiguration {
    @Bean
    public S3Client s3Client() {
        return new S3ServerMock();
    }
}
