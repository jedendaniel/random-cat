package com.ddd.cat.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt")
@Getter
@Setter
@NoArgsConstructor
public class JwtProperties {

    String secret;
    long expirationMs;
}
