package com.example.ragcode.service;

import com.example.ragcode.rag.ChunkingUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CodeProcessingService {

    private final EmbeddingService embeddingService;
    private final RetrievalService retrievalService;
    private final LLMService llmService;

    public CodeProcessingService(
            EmbeddingService embeddingService,
            RetrievalService retrievalService,
            LLMService llmService
    ) {
        this.embeddingService = embeddingService;
        this.retrievalService = retrievalService;
        this.llmService = llmService;
    }

    public void processFile(MultipartFile file) throws Exception {

        String content = new String(file.getBytes());

        List<String> chunks = ChunkingUtil.chunkCode(content);

        for (String chunk : chunks) {
            embeddingService.storeEmbedding(chunk);
        }
    }

    public String answerQuestion(String question) {

        List<String> relevantChunks = retrievalService.retrieve(question);

        return llmService.generateExplanation(question, relevantChunks);
    }
}