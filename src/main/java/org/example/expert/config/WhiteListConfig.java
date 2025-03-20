package org.example.expert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class WhiteListConfig {

    @Bean
    public List<String> whiteList(){
        return List.of(
                "/auth/signup",
                "/auth/signin",
                "/health"
        );
    }
}
