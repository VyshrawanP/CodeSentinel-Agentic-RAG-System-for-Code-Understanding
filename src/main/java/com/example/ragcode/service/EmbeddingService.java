package com.example.ragcode.service;

import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    public void storeEmbedding(String chunk) {

        // Step 1
        // call embedding API

        // Step 2
        // store vector + chunk into vector DB

        System.out.println("Embedding stored for chunk");
    }
}