CodeSentinel — Agentic RAG System for Code Understanding

CodeSentinel is an agentic backend system designed to analyze and explain Java codebases using a Retrieval Augmented Generation (RAG) architecture. Built with Spring Boot, the platform processes source code, extracts semantic meaning through embeddings, and retrieves relevant code segments from a vector store before invoking a large language model to generate contextual explanations.

The system follows a modular AI pipeline that enables scalable reasoning over large codebases. By combining code chunking, embedding generation, vector similarity search, and LLM-driven analysis, CodeSentinel provides intelligent insights into code structure, logic, and behavior.

This project demonstrates how modern backend services can orchestrate AI pipelines to build developer-focused tools capable of understanding complex software systems.

Core Capabilities

Java file ingestion and processing

semantic code chunking for large codebases

embedding generation for code understanding

vector similarity search for contextual retrieval

LLM-powered reasoning for code explanation

Architecture Overview
Code Upload
     ↓
Code Chunking
     ↓
Embedding Generation
     ↓
Vector Database Indexing
     ↓
Semantic Retrieval
     ↓
LLM Reasoning
     ↓
Contextual Code Explanation
Tech Stack

Spring Boot for backend APIs

Vector database for semantic retrieval

Embedding models for code representation

LLM APIs for contextual reasoning