package com.epam.esm.zotov.mjcschool.api.security;

import com.epam.esm.zotov.mjcschool.api.security.jwt.JwtAuthorizationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtAuthorizationFilter authorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/**/certificates").permitAll()
                .antMatchers(HttpMethod.POST, "/**/users", "/**/authorization").permitAll()
                .antMatchers(HttpMethod.POST, "/**/users/orders").hasAnyAuthority(Roles.USER, Roles.ADMIN)
                .antMatchers(HttpMethod.GET, "/**/tags/**", "/**/orders/**").hasAnyAuthority(Roles.USER, Roles.ADMIN)
                .anyRequest().hasAuthority(Roles.ADMIN);

        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void setAuthorizationFilter(JwtAuthorizationFilter authorizationFilter) {
        this.authorizationFilter = authorizationFilter;
    }
}