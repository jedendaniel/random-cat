package com.ddd.cat.security;

import com.ddd.cat.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Clock;
import java.util.Date;

public interface JwtService {

    String generateToken(UserDetails userDetails);
    String extractUsername(String token);
    boolean isTokenValid(String token);

    @Service
    class DefaultJwtService implements JwtService {

        private final JwtProperties jwtProperties;
        private final Clock clock;
        private final Key key;

        public DefaultJwtService(JwtProperties jwtProperties, Clock clock) {
            this.jwtProperties = jwtProperties;
            this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
            this.clock = clock;
        }

        public String generateToken(UserDetails userDetails) {
            long currentMillis = clock.millis();
            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .claim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .issuedAt(new Date(currentMillis))
                    .expiration(new Date(currentMillis + jwtProperties.getExpirationMs()))
                    .signWith(key)
                    .compact();
        }

        public String extractUsername(String token) {
            return parse(token).getPayload().getSubject();
        }

        public boolean isTokenValid(String token) {
            try {
                parse(token);
                return true;
            } catch (JwtException | IllegalArgumentException e) {
                return false;
            }
        }

        private Jws<Claims> parse(String token) {
            return Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token);
        }
    }
}
