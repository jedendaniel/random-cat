package com.ddd.cat.service;

import com.ddd.cat.properties.AwsProperties;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class S3ServiceTest {
    private final AwsProperties awsProperties = mock(AwsProperties.class);
    private final S3Client s3Client = mock(S3Client.class);
    private final S3Service s3Service = new S3Service(awsProperties, s3Client);

    @Test
    void shouldListObjectNames() {
        when(awsProperties.getBucketName()).thenReturn("bucket");
        ListObjectsV2Response listObjectsV2Response = ListObjectsV2Response.builder()
                .contents(List.of(S3Object.builder().key("pic1").build())).build();
        when(s3Client.listObjectsV2(any(ListObjectsV2Request.class))).thenReturn(listObjectsV2Response);
        List<String> catPicsFileNames = s3Service.listCatKeys();
        assertFalse(catPicsFileNames.isEmpty());
    }
}