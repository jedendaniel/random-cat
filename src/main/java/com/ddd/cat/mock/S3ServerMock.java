package com.ddd.cat.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.io.InputStream;
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
    public static final String RESOURCES_MAIN = "";

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
                .contents(listObjects(listObjectsV2Request.bucket()).stream()
                        .map(objectName -> S3Object.builder().key(objectName).build())
                        .toList())
                .build();
    }

    @Override
    public ResponseBytes<GetObjectResponse> getObjectAsBytes(GetObjectRequest getObjectRequest)
            throws AwsServiceException, SdkClientException {
        try (InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream(RESOURCES_MAIN + getObjectRequest.bucket() + "/" + getObjectRequest.key())) {
            return ResponseBytes.fromByteArray(GetObjectResponse.builder().build(), Objects.requireNonNull(in).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> listObjects(String subDirName) {
        URL picsUrl = Objects.requireNonNull(S3ServerMock.class.getClassLoader().getResource(RESOURCES_MAIN + subDirName));
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
