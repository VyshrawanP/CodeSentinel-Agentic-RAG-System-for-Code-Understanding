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
            .baseUrl("https://api.groq.com/openai/v1")
            .build();

    @SuppressWarnings("unchecked")
    public String generateResponse(String prompt) {

        Map<String, Object> request = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        Map<String, Object> response = webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
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