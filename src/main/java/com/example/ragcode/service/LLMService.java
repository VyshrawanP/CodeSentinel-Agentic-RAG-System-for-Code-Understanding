package com.example.ragcode.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LLMService {

    private final GrokService grokService;

    public LLMService(GrokService grokService) {
        this.grokService = grokService;
    }
        public String askLLM(String prompt) {
        return grokService.generateResponse(prompt);
    }

    public String generateExplanation(String question, List<String> chunks) {

        String context = String.join("\n", chunks);

        String prompt = """
        Context:
        %s

        Question:
        %s

        Explain the Java code clearly.
        """.formatted(context, question);

        return grokService.generateResponse(prompt);
    }
}