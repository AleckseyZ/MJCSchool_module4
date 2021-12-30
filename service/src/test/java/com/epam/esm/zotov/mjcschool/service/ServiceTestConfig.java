package com.epam.esm.zotov.mjcschool.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Profile("test")
public class ServiceTestConfig {
    @Bean
    public PropertySourcesPlaceholderConfigurer pspConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}