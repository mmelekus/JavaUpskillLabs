package com.example.apiclient.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        // TODO 6: Before calling execution.execute(), log the HTTP method and URI.
        // Use: log.info(">>> {} {}", request.getMethod(), request.getURI())
        // After execution.execute() returns, log the response status code.
        // Use: log.info("<<< {}", response.getStatusCode())
        // Return the response.
        // Hint: ClientHttpResponse response = execution.execute(request, body);
        log.info(">>> {} {}", request.getMethod(), request.getURI());
        ClientHttpResponse response = execution.execute(request, body);
        log.info("<<< {}", response.getStatusCode());
        return response;
    }
}