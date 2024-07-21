package com.ddd.cat.service;

import com.ddd.cat.properties.AwsProperties;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

@Service
public class S3Service {

    private final AwsProperties awsProperties;
    private final S3Client s3Client;

    public S3Service(AwsProperties awsProperties, S3Client s3Client) {
        this.awsProperties = awsProperties;
        this.s3Client = s3Client;
    }

    public List<String> listCatKeys() {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(awsProperties.getBucketName())
                .build();
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);
        return listObjectsV2Response.contents().stream().map(S3Object::key).toList();
    }

    public byte[] getCatPicAsByteArray(String key) {
        GetObjectRequest getRequest = GetObjectRequest.builder().bucket(awsProperties.getBucketName()).key(key).build();
        return s3Client.getObjectAsBytes(getRequest).asByteArray();
    }
}
