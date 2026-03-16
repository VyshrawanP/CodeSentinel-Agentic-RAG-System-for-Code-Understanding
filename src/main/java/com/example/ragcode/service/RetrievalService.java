package com.example.ragcode.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrievalService {

    public List<String> retrieve(String question) {

        // Step 1
        // convert question to embedding

        // Step 2
        // search vector DB

        // Step 3
        // return top matching chunks

        return List.of("retrieved code chunk example");
    }
}