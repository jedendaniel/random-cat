package com.ddd.cat.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@Setter
@Getter
public class AwsProperties {
    private String awsRegion;
    private String bucketName;

    public AwsProperties() {
    }
}
