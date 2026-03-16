package com.example.ragcode.controller;

import com.example.ragcode.service.CodeProcessingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/code")
public class CodeController {

    private final CodeProcessingService service;

    public CodeController(CodeProcessingService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        service.processFile(file);
        return "File processed successfully";
    }
        @GetMapping("/test")
    public String test() {
        return "CodeSentinel running";
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestBody String question) {
        return service.answerQuestion(question);
    }
}