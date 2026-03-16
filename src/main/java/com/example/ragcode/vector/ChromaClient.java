package com.example.ragcode.vector;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ChromaClient {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://127.0.0.1:8001/api/v2")
            .build();

    private final String COLLECTION =
            "/tenants/default_tenant/databases/default_database/collections/c931b9b0-27ed-4bae-bbce-b0496184879d";

    // STORE CHUNK
    public void storeChunk(String chunk) {

        System.out.println("ChromaClient called");
        System.out.println("Sending request to Chroma...");

        Map<String, Object> body = Map.of(
                "ids", List.of(UUID.randomUUID().toString()),
                "documents", List.of(chunk),
                "embeddings", List.of(List.of(0.1,0.2,0.3,0.4))
        );

        try {

            String response = webClient.post()
                    .uri(COLLECTION + "/add")
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

    // SEARCH CHUNKS
    public List<String> searchChunks(List<Double> embedding) {

        System.out.println("Searching Chroma for similar chunks");

        Map<String, Object> body = Map.of(
                "query_embeddings", List.of(embedding),
                "n_results", 3
        );

        Map response = webClient.post()
                .uri(COLLECTION + "/query")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List<List<String>> documents =
                (List<List<String>>) response.get("documents");

        return documents.get(0);
    }
}