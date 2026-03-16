package com.example.ragcode.rag;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class VectorStoreService {

    public void storeVector(List<Double> embedding, String chunk) {

        // send to vector database
        // example: Chroma

        System.out.println("Vector stored");
    }
}