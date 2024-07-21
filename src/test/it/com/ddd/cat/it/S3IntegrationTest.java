package com.ddd.cat.it;

import com.ddd.cat.properties.AwsProperties;
import com.ddd.cat.service.S3Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class S3IntegrationTest {
    @Autowired
    private S3Service s3Service;
    @Autowired
    private AwsProperties awsProperties;

    @Test
    void shouldListObjects() {
        List<String> catPicKeys = s3Service.listCatKeys();
        assertEquals(2, catPicKeys.size());
    }

    @Test
    void shouldGetObjectAsBytes() {
        List<String> catPicNames = s3Service.listCatKeys();
        assertFalse(catPicNames.isEmpty());
        byte[] objectAsBytes = s3Service.getCatPicAsByteArray(catPicNames.get(0));
        assertTrue(objectAsBytes.length > 0);
    }
}
