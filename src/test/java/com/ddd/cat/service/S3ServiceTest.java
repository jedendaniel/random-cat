package com.ddd.cat.service;

import com.ddd.cat.properties.AwsProperties;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class S3ServiceTest {
    private static final AwsProperties awsProperties = mock(AwsProperties.class);
    static {
        when(awsProperties.getAwsRegion()).thenReturn("eu-central-1");
        when(awsProperties.getBucketName()).thenReturn("ddd-cat-bucket");
    }
    private final S3Service s3Service = new S3Service(awsProperties);

    @Test
    void shouldListObjectNames() {
        List<String> catPicsFileNames = s3Service.getCatPicsFileNames();
        assertFalse(catPicsFileNames.isEmpty());
    }
}