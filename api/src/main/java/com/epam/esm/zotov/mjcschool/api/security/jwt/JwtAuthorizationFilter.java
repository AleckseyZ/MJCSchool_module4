package com.epam.esm.zotov.mjcschool.api.security.jwt;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final int EXPECTED_TOKEN_POSITION = 7;
    private JwtOperator jwtOperator;
    private JwtTokenValidator tokenValidator;
    private UserDetailsService userService;

    @Autowired
    public void setJwtUtil(JwtOperator jwtOperator) {
        this.jwtOperator = jwtOperator;
    }

    @Autowired
    public void setUserService(UserDetailsService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTokenValidator(JwtTokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (Objects.nonNull(token)) {
            token = token.substring(EXPECTED_TOKEN_POSITION);
            if (tokenValidator.validateToke(token)) {
                String username = jwtOperator.extractSubject(token);
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (Objects.nonNull(userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}