package com.epam.esm.zotov.mjcschool.api.security.jwt;

import java.util.Objects;

import com.epam.esm.zotov.mjcschool.dataaccess.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user) {
        return Jwts.builder().setSubject(user.getUsername()).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public boolean validateToke(String token) {
        boolean isValid = true;
        try {
            isValid = Objects.nonNull(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }

    public String extractSubject(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}