package com.example.employeehandbook.ingestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ResourceLoader {

  private final ApplicationContext applicationContext;
  private final ResourceReader resourceReader;
  private final DocumentMetadataEnricher documentMetadataEnricher;
  private final DocumentChunker documentChunker;

  /**
   * Loads all handbook files from the classpath, converts each file into Spring
   * AI Documents,
   * enriches them with source metadata, splits them into final RAG chunks, and
   * returns the complete chunk list ready to be embedded and stored in the vector
   * store.
   *
   * Flow:
   * handbook resources -> ResourceReader -> metadata enrichment ->
   * TokenTextSplitter -> chunks
   */
  public List<Document> loadAll() throws IOException {

    Resource[] resources = applicationContext.getResources("classpath*:handbook/*.*");

    List<Document> chunks = new ArrayList<>();

    for (Resource resource : resources) {

      // 1. Read physical file
      List<Document> resourceDocs = resourceReader.read(resource);

      // 2. Add metadata
      documentMetadataEnricher.enrich(resourceDocs, resource);

      // 3. Split into final chunks(using TokenTextSplitter)
      List<Document> resourceChunks = documentChunker.split(resourceDocs);

      // 4. Add all chunks to final result
      chunks.addAll(resourceChunks);

    }

    return chunks;

  }

}
