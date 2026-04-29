package com.example.bankbff.client;

import com.example.bankbff.dto.AccountDto;
import com.example.bankbff.dto.TransferRequestDto;
import com.example.bankbff.dto.TransferResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Client for the banking resource server.
 *
 * Wraps the configured WebClient with typed methods for the resource
 * server's endpoints. The WebClient already has the OAuth2 filter applied
 * (see WebClientConfig), so bearer tokens are attached automatically.
 */
@Component
public class BankingApiClient {

    private final WebClient bankingWebClient;

    public BankingApiClient(WebClient bankingWebClient) {
        this.bankingWebClient = bankingWebClient;
    }

    public List<AccountDto> getAccounts() {
        // TODO 6.1: Call GET /accounts on the resource server and return
        // the response as a List<AccountDto>.
        //
        // Pattern:
        //   return bankingWebClient.get()
        //       .uri("/accounts")
        //       .retrieve()
        //       .bodyToMono(new ParameterizedTypeReference<List<AccountDto>>() {})
        //       .block();
        //
        // Why .block()? WebClient is reactive and returns a Mono. Since our
        // controller is synchronous Spring MVC, we block to get the value.

        return bankingWebClient.get()
                .uri("/accounts")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<AccountDto>>() {})
                .block();
    }

    public TransferResponseDto postTransfer(TransferRequestDto request) {
        // TODO 6.2: Call POST /transfers on the resource server with the
        // given request body and return the response as a TransferResponseDto.
        //
        // Pattern:
        //   return bankingWebClient.post()
        //       .uri("/transfers")
        //       .bodyValue(request)
        //       .retrieve()
        //       .bodyToMono(TransferResponseDto.class)
        //       .block();

        return bankingWebClient.post()
                .uri("/transfers")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TransferResponseDto.class)
                .block();
    }
}