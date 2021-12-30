package com.epam.esm.zotov.mjcschool.api.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@PropertySource("classpath:/authentication.properties")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Value("${www.header}")
    private String header;
    @Value("${www.header.domain}")
    private String domain;
    @Value("${unauthorized}")
    private String unauthorized;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.setHeader(header, domain);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, unauthorized);
    }
}