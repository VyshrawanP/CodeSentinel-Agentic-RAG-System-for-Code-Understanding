package com.example.ragcode.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CodeProcessingService {

    private final ChunkingService chunkingService;
    private final EmbeddingService embeddingService;
    private final RetrievalService retrievalService;
    private final LLMService llmService;

    public CodeProcessingService(
            EmbeddingService embeddingService,
            RetrievalService retrievalService,
            LLMService llmService,
            ChunkingService chunkingService
    ) {
        this.embeddingService = embeddingService;
        this.retrievalService = retrievalService;
        this.llmService = llmService;
        this.chunkingService = chunkingService;
    }

    // INGEST CODE
    public void processFile(MultipartFile file) throws Exception {

        String code = new String(file.getBytes());

        List<String> chunks = chunkingService.chunkCode(code);

        for(String chunk : chunks){
            embeddingService.storeEmbedding(chunk);
        }
    }

    // TEST GROQ
    public String testGrok() {

        String prompt = "Explain what a Java class is in simple terms.";

        return llmService.askLLM(prompt);
    }

    // MAIN RAG PIPELINE
    public String answerQuestion(String question) {

        System.out.println("User Question:");
        System.out.println(question);

        // STEP 1
        List<String> relevantChunks =
                retrievalService.retrieve(question);

        // STEP 2
        String answer =
                llmService.generateExplanation(question, relevantChunks);

        return answer;
    }
}