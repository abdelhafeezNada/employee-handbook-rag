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

  public List<Document> loadAll() throws IOException {

    Resource[] resources = applicationContext.getResources("classpath*:handbook/*.*");

    List<Document> documents = new ArrayList<>();

    for (Resource resource : resources) {
      // documents.addAll(resourceReader.read(resource));

      List<Document> resourceDocs = resourceReader.read(resource);
      documentMetadataEnricher.enrich(resourceDocs, resource);
      documents.addAll(resourceDocs);

    }

    return documents;

  }

}
