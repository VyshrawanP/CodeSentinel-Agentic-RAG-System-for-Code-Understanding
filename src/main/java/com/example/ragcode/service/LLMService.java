package com.example.ragcode.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LLMService {

    public String generateExplanation(String question, List<String> chunks) {

        String context = String.join("\n", chunks);

        String prompt = """
        Context:
        %s

        Question:
        %s

        Explain clearly.
        """.formatted(context, question);

        // call OpenAI API here

        return "Generated explanation based on retrieved code.";
    }
}