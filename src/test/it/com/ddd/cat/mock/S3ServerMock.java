package com.ddd.cat.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class S3ServerMock implements S3Client {
    public static final Logger LOGGER = LoggerFactory.getLogger(S3ServerMock.class.getSimpleName());

    public S3ServerMock() {
    }

    @Override
    public String serviceName() {
        return "S3ServerMock";
    }

    @Override
    public void close() {

    }

    @Override
    public ListObjectsV2Response listObjectsV2(ListObjectsV2Request listObjectsV2Request) throws AwsServiceException, SdkClientException {
        ListObjectsV2Response.Builder responseBuilder = ListObjectsV2Response.builder();
        return responseBuilder
                .contents(listObjects().stream()
                        .map(objectName -> S3Object.builder().key(objectName).build())
                        .toList())
                .build();
    }

    private Set<String> listObjects() {
        URL picsUrl = Objects.requireNonNull(S3ServerMock.class.getClassLoader().getResource("it/mock-pics"));
        try (Stream<Path> stream = Files.list(Paths.get(picsUrl.toURI()))) {
            return stream
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        } catch (IOException | URISyntaxException e) {
            LOGGER.error(e.getMessage());
        }
        return Set.of();
    }
}
