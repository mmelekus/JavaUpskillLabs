package com.example.bankbff.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient configuration for outbound calls to the resource server.
 *
 * The WebClient is configured with Spring Security's OAuth2 filter, which:
 *
 *  - Looks up the authenticated user's OAuth2AuthorizedClient (which holds
 *    their access and refresh tokens).
 *  - If the access token is near expiry, refreshes it transparently.
 *  - Attaches the access token as an Authorization: Bearer header on every
 *    outbound request.
 *
 * Application code uses the WebClient like any normal HTTP client and never
 * touches tokens directly.
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient bankingWebClient(
            OAuth2AuthorizedClientManager authorizedClientManager,
            @Value("${banking.resource-server.base-url}") String baseUrl) {

        // TODO 5.1: Create the OAuth2 filter.
        //
        // Pattern:
        //   ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
        //       new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);

        // TODO 5.2: Tell the filter to use the 'bank-auth' registration by default.
        //
        // Pattern:
        //   oauth2.setDefaultClientRegistrationId("bank-auth");
        oauth2.setDefaultClientRegistrationId("bank-auth");

        // TODO 5.3: Build a WebClient with the base URL set and the OAuth2 filter applied.
        //
        // Pattern:
        //   return WebClient.builder()
        //       .baseUrl(baseUrl)
        //       .apply(oauth2.oauth2Configuration())
        //       .build();
        return WebClient.builder()
                .baseUrl(baseUrl)
                .apply(oauth2.oauth2Configuration())
                .build();
    }
}