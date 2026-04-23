package com.example.apiclient.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class BearerTokenFilter {

    private static final Logger log = LoggerFactory.getLogger(BearerTokenFilter.class);

    private final StaticTokenProvider tokenProvider;

    public BearerTokenFilter(StaticTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    // TODO 18: Implement filter(), returning an ExchangeFilterFunction.
    //
    // Use ExchangeFilterFunction.ofRequestProcessor(request -> { ... })
    // Inside the lambda:
    //   1. Call tokenProvider.getToken() to retrieve the token value.
    //   2. Log that a token is being attached, but log only the first 8 characters
    //      followed by "..." to avoid exposing the full token value in logs.
    //      Example: log.debug("Attaching token: {}...", token.substring(0, 8))
    //   3. Build a new request with the Authorization header:
    //        ClientRequest.from(request)
    //            .header("Authorization", "Bearer " + token)
    //            .build()
    //   4. Wrap the result in Mono.just() and return it.
    //
    // Never log the full token value. This is a security requirement, not a style choice.
    public ExchangeFilterFunction filter() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            String token = tokenProvider.getToken();
            log.debug("Attaching token: {}...", token.substring(0, 8));
            ClientRequest authenticatedRequest = ClientRequest.from(request)
                    .header("Authorization", "Bearer " + token)
                    .build();
            return Mono.just(authenticatedRequest); // Replace with your implementation
        });
    }
}