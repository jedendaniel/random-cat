package com.ddd.cat.controller;

import com.ddd.cat.config.SecurityConfig;
import com.ddd.cat.controller.model.Cat;
import com.ddd.cat.domain.RandomCatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(CatController.class)
@Import(SecurityConfig.class)
public class CatControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RandomCatService randomCatService;

    @Test
    void test() {
        RestTestClient client = RestTestClient.bindTo(mockMvc).build();
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
