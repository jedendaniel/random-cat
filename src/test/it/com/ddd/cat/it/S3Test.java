package com.ddd.cat.it;

import com.ddd.cat.mock.S3ServerMock;
import com.ddd.cat.properties.AwsProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {AwsProperties.class, S3ServerMock.class})
public class S3Test {
    @Autowired
    private S3Client s3Client;

    @Test
    void shouldListObjects() {
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(ListObjectsV2Request.builder().build());
        assertEquals(4, listObjectsV2Response.contents().size());
    }
}
