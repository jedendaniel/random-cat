package com.ddd.cat.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws")
@Setter
@Getter
public class AwsProperties {
    private Boolean enabled;
    private String region;
    private String bucketName;

    public AwsProperties() {
    }
}
