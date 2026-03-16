package com.example.ragcode.service;

import com.example.ragcode.rag.ChunkingUtil;
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

    public void processFile(MultipartFile file) throws Exception {

      String code = new String(file.getBytes());

List<String> chunks = chunkingService.chunkCode(code);

for(String chunk : chunks){
    embeddingService.storeEmbedding(chunk);
}
    }
    public String testGrok() {

    String prompt = "Explain what a Java class is in simple terms.";

    return llmService.askLLM(prompt);
}

    public String answerQuestion(String question) {

        List<String> relevantChunks = retrievalService.retrieve(question);

        return llmService.generateExplanation(question, relevantChunks);
    }
}