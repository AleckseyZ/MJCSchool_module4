package com.epam.esm.zotov.mjcschool.api.security.jwt;

import java.time.Instant;
import java.util.Date;

import com.epam.esm.zotov.mjcschool.dataaccess.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtOperator {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.tokenLifespan}")
    private int tokenLifespan;
    @Value("${jwt.refreshLifespan}")
    private int refreshLifespan;

    public String createToken(User user) {
        return Jwts.builder().setSubject(user.getUsername())
                .setExpiration(Date.from(Instant.now().plusSeconds(tokenLifespan)))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String createRefreshToken(User user) {
        return Jwts.builder().setSubject(user.getUsername())
                .setExpiration(Date.from(Instant.now().plusSeconds(refreshLifespan)))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String extractSubject(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}