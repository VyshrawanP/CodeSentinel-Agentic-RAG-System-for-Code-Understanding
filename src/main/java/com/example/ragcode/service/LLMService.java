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

    StringBuilder context = new StringBuilder();

    for(String chunk : chunks){
        context.append(chunk).append("\n\n");
    }

    String prompt = """
Context:
%s

Question:
%s

Answer clearly and explain the code.
""".formatted(context.toString(), question);

    System.out.println("LLM Prompt:");
    System.out.println(prompt);

    String response = grokService.generateResponse(prompt);

    System.out.println("LLM Response:");
    System.out.println(response);

    return response;
}
}