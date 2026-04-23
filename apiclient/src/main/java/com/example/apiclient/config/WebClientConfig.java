package com.example.apiclient.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    // TODO 8: Create a WebClient bean named "employeeWebClient".
    // Configure the Reactor Netty HttpClient with:
    //   - Connection timeout: 3000 milliseconds (ChannelOption.CONNECT_TIMEOUT_MILLIS)
    //   - Read timeout: 5 seconds (ReadTimeoutHandler added via doOnConnected)
    //   - Response timeout: 5 seconds (HttpClient.responseTimeout)
    // Build a WebClient using WebClient.builder()
    //   - Set the base URL to "http://localhost:8081"
    //   - Apply the ReactorClientHttpConnector wrapping your HttpClient
    //
    // Hint - HttpClient setup:
    //   HttpClient httpClient = HttpClient.create()
    //       .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
    //       .responseTimeout(Duration.ofSeconds(5))
    //       .doOnConnected(conn ->
    //           conn.addHandlerLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS)));
    @Bean
    @Primary
    public WebClient employeeWebClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .responseTimeout(Duration.ofSeconds(5))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS)));

        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build(); // Replace with your implementation
    }

    @Bean
    public WebClient authenticatedWebClient(StaticTokenProvider tokenProvider) {
        var bearerTokenFilter = new BearerTokenFilter(tokenProvider);

        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .filter(bearerTokenFilter.filter())
                .build();
    }
}