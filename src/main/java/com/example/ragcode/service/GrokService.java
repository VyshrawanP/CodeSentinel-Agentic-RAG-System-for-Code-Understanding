package com.example.ragcode.service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class GrokService {

    @Value("${xai.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.x.ai/v1")
            .build();

    @SuppressWarnings("unchecked")
    public String generateResponse(String prompt) {

        Map<String, Object> request = Map.of(
                "model", "grok-beta",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        Map<String, Object> response = webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List<Map<String, Object>> choices =
                (List<Map<String, Object>>) response.get("choices");

        Map<String, Object> message =
                (Map<String, Object>) choices.get(0).get("message");

        return (String) message.get("content");
    }
}