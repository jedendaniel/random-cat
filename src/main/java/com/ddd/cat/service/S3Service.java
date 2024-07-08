package com.ddd.cat.service;

import com.ddd.cat.properties.AwsProperties;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

@Service
public class S3Service {

    private final AwsProperties awsProperties;
    private final S3Client s3Client;

    public S3Service(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
        this.s3Client = s3Client(awsProperties);
    }

    public List<String> getCatPicsFileNames() {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(awsProperties.getBucketName())
                .build();
        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);
        return listObjectsV2Response.contents().stream().map(S3Object::key).peek(System.out::println).toList();
    }



    private S3Client s3Client(AwsProperties awsProperties) {
        return S3Client.builder()
                .region(Region.of(awsProperties.getAwsRegion()))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
