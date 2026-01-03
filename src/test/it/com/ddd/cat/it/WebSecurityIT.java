package com.ddd.cat.it;

import com.ddd.cat.controller.model.Cat;
import com.ddd.cat.model.Role;
import com.ddd.cat.model.User;
import com.ddd.cat.security.CustomUserDetails;
import com.ddd.cat.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class WebSecurityIT extends BaseIT {

    @LocalServerPort
    private int port;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    private RestTestClient client;

    @BeforeEach
    void setUp() {
        client = RestTestClient.bindToServer().build();
    }

    @Test
    void shouldReturnOkStatusAndCatPic() {
        Cat responseBody = client.get()
                .uri("http://localhost:" + port + "/cat")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Cat>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertThat(responseBody.pic())
                .isBase64();
    }
    @Test
    void shouldReturnForbiddenWhenCalledWithoutBearerToken() {
        client.get()
                .uri("http://localhost:" + port + "/cat/premium")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void test() {
        String token = "validTokenSignature";
        String tokenHeader = "Bearer " + token;
        when(jwtService.isTokenValid(token)).thenReturn(true);
        when(jwtService.extractUsername(token)).thenReturn("username");
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRoles(Set.of(Role.USER));
        when(userDetailsService.loadUserByUsername("username")).thenReturn(new CustomUserDetails(user));

        Cat responseBody = client.get()
                .uri("http://localhost:" + port + "/cat/premium")
                .header("Authorization", tokenHeader)
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
