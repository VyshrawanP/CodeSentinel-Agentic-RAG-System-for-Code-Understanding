package com.example.ragcode.service;

import org.springframework.stereotype.Service;
import com.example.ragcode.vector.ChromaClient;

@Service
public class EmbeddingService {

    private final ChromaClient chromaClient;

    public EmbeddingService(ChromaClient chromaClient) {
        this.chromaClient = chromaClient;
    }

    public void storeEmbedding(String chunk) {

        chromaClient.storeChunk(chunk);

        System.out.println("Embedding stored for chunk:");
        System.out.println(chunk);
    }
}