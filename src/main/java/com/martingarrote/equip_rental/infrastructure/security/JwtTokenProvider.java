package com.martingarrote.equip_rental.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.martingarrote.equip_rental.domain.user.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.expiration-hours}")
    private long expirationHours;

    private static final long SECONDS_IN_AN_HOUR = 3600L;

    public String generateToken(UserEntity user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getEmail())
                .withClaim("role", user.getRole().name())
                .withClaim("name", user.getName())
                .withIssuedAt(Instant.now())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
    }

    public String getEmailFromToken(String token) {
        if (!validateToken(token)) {
            return null;
        }
        return verifyAndDecode(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        if (!validateToken(token)) {
            return null;
        }
        return verifyAndDecode(token).getClaim("role").asString();
    }

    public boolean validateToken(String token) {
        try {
            verifyAndDecode(token);
            return true;
        } catch (JWTVerificationException e) {
            log.warn("Falha na verificação do JWT: {}", e.getMessage());
            return false;
        }
    }

    private DecodedJWT verifyAndDecode(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
    }

    private Instant generateExpirationDate() {
        return Instant.now().plusSeconds(expirationHours * SECONDS_IN_AN_HOUR);
    }
}