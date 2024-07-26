package com.ddd.cat.config;

import com.ddd.cat.mock.S3ServerMock;
import com.ddd.cat.properties.AwsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    @ConditionalOnExpression(value = "${aws.enabled:false}")
    public S3Client s3Client(AwsProperties awsProperties) {
        return S3Client.builder()
                .region(Region.of(awsProperties.getRegion()))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(S3Client.class)
    public S3Client s3ServerMock(AwsProperties awsProperties) {
        return new S3ServerMock();
    }
}
