package com.ddd.cat.controller;

import com.ddd.cat.controller.model.Cat;
import com.ddd.cat.domain.RandomCatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CatControllerTest {

    private RestTestClient client;
    private final RandomCatService randomCatService = mock(RandomCatService.class);

    @BeforeEach
    void setUp() {
        client = RestTestClient.bindToController(new CatController(randomCatService)).build();
    }

    @Test
    void shouldGetResponseWithBaseCatAndStatusOk() {
        when(randomCatService.getBaseCatPic()).thenReturn("byteArray");

        Cat responseBody = client.get()
                .uri("/api/v1/cat")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Cat>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertThat(responseBody.pic())
                .isEqualTo("byteArray");
    }

}