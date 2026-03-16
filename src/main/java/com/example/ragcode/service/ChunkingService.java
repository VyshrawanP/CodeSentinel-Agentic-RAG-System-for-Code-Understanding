package com.example.ragcode.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkingService {

    public List<String> chunkCode(String code) {

        int chunkSize = 400;
        List<String> chunks = new ArrayList<>();

        for (int i = 0; i < code.length(); i += chunkSize) {
            int end = Math.min(code.length(), i + chunkSize);
            chunks.add(code.substring(i, end));
        }

        return chunks;
    }
}