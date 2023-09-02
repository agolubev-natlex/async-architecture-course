package ru.natlex.commons.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@AllArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    public static final SecretKey KEY = Keys.hmacShaKeyFor(Base64.getEncoder().encode("суперсекретнаястрокавбайты".getBytes(StandardCharsets.UTF_8)));

    public String createToken(String publicId, String role) {

        Date expiryDate = Date.from(Instant.now().plus(10, ChronoUnit.DAYS));

        return Jwts.builder()
                .setSubject(publicId)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(KEY, SignatureAlgorithm.HS512)
                .claim("publicId", publicId)
                .claim("role", role)
                .compact();
    }

    public Claims validateToken(String bearerToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(bearerToken)
                    .getBody();
        } catch (Exception e) {
            log.error("Ошибка при валидации токена", e);
            return null;
        }
    }
}
