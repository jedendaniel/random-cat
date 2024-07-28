package com.ddd.cat.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "development")
@Setter
@Getter
public class DevelopmentProperties {
    private Boolean templateResolverCacheable;
}
