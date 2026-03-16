package com.example.ragcode.rag;

import java.util.ArrayList;
import java.util.List;

public class ChunkingUtil {

    public static List<String> chunkCode(String code) {

        int chunkSize = 500;

        List<String> chunks = new ArrayList<>();

        for (int i = 0; i < code.length(); i += chunkSize) {

            chunks.add(code.substring(
                    i,
                    Math.min(code.length(), i + chunkSize)
            ));
        }

        return chunks;
    }
}