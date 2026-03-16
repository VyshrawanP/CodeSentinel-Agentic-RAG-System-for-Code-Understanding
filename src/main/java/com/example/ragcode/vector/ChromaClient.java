package com.example.ragcode.vector;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ChromaClient {

    private final WebClient webClient;

    // your collection UUID
    private static final String COLLECTION_ID =
            "6479f30c-09e9-402d-8443-ad4378bafe75";

    public ChromaClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8001/api/v2")
                .build();
    }

   public void storeChunk(String chunk) {

    System.out.println("ChromaClient called");

    List<Double> embedding = List.of(
            Math.random(),
            Math.random(),
            Math.random(),
            Math.random()
    );

    Map<String, Object> body = Map.of(
            "ids", List.of(UUID.randomUUID().toString()),
            "documents", List.of(chunk),
            "embeddings", List.of(embedding)
    );

    System.out.println("Sending request to Chroma...");

    try {

        String response = webClient.post()
                .uri("/tenants/default_tenant/databases/default_database/collections/6479f30c-09e9-402d-8443-ad4378bafe75/add")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Chroma response: " + response);

    } catch (Exception e) {

        System.out.println("ERROR connecting to Chroma");
        e.printStackTrace();

    }

    System.out.println("Finished Chroma call");
}
}