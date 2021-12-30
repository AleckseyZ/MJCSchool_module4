package com.epam.esm.zotov.mjcschool.api.security.jwt;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenValidator {
    @Value("${jwt.secret}")
    private String secret;

    public boolean validateToke(String token) {
        boolean isValid = true;
        try {
            isValid = Objects.nonNull(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            isValid = false;
        }
        return isValid;
    }
}