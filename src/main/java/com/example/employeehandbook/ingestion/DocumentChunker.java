package com.example.employeehandbook.ingestion;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

@Component
public class DocumentChunker {

  private final TokenTextSplitter tokenTextSplitter;

  public DocumentChunker() {
    this.tokenTextSplitter = TokenTextSplitter.builder().build();
  }

  public List<Document> split(List<Document> documents) {
    return tokenTextSplitter.apply(documents);
  }
}
