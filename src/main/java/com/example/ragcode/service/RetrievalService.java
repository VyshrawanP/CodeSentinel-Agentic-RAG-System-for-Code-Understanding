package com.example.ragcode.service;

import com.example.ragcode.vector.ChromaClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrievalService {

    private final ChromaClient chromaClient;

    public RetrievalService(ChromaClient chromaClient) {
        this.chromaClient = chromaClient;
    }

    public List<String> retrieve(String question) {

        System.out.println("RetrievalService called");

        // Step 1
        // Convert question to embedding
        // (Currently dummy vector — later replace with real embedding API)
        List<Double> embedding = generateEmbedding(question);

        // Step 2
        // Search Chroma
        List<String> results = chromaClient.searchChunks(embedding);

        System.out.println("Retrieved chunks:");
        results.forEach(System.out::println);

        return results;
    }

    private List<Double> generateEmbedding(String text) {

        // Dummy embedding for now
        return List.of(0.1, 0.2, 0.3, 0.4);

    }
}