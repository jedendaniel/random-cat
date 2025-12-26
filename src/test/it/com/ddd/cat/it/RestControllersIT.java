package com.ddd.cat.it;

import com.ddd.cat.controller.model.Cat;
import com.ddd.cat.domain.RandomCatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestControllersIT extends BaseIT {

    private RestTestClient client;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        client = RestTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void test() {
        Cat responseBody = client.get()
                .uri("/api/v1/cat")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Cat>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertThat(responseBody.pic())
                .isBase64();
    }
}
