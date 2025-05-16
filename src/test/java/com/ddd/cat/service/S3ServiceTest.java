package com.ddd.cat.service;

import com.ddd.cat.domain.S3Service;
import com.ddd.cat.properties.AwsProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @Mock
    private AwsProperties awsProperties;
    @Mock
    private S3Client s3Client;
    @InjectMocks
    private S3Service s3Service;

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