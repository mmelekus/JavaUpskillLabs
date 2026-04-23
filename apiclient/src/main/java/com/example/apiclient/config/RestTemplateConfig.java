package com.example.apiclient.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    // TODO 1: Use the RestTemplateBuilder to create a RestTemplate bean.
    // Set the root URI to "http://localhost:8081".
    // Set a connection timeout of 3 seconds.
    // Set a read timeout of 5 seconds.
    // Hint: builder.rootUri(...).connectTimeout(...).readTimeout(...)
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri("http://localhost:8081")
                .connectTimeout(Duration.ofSeconds(3))
                .readTimeout(Duration.ofSeconds(5))
                .additionalInterceptors(new LoggingInterceptor())
                .build(); // Replace with your implementation
    }
}