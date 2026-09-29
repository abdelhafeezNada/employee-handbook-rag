package com.example.employeehandbook.ingestion;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class DocumentMetadataEnricher {

  public void enrich(List<Document> documents, Resource resource) {

    String filename = resource.getFilename();

    if (filename == null)
      return;
    String fileType = getFileType(filename);

    for (Document doc : documents) {
      Map<String, Object> metadata = doc.getMetadata();
      metadata.put("source", filename);
      metadata.put("fileType", fileType);
    }
  }

  private String getFileType(String filename) {

    int dotIndex = filename.lastIndexOf(".");

    if (dotIndex == -1)
      return "Unknown";

    return filename.substring(dotIndex + 1).toLowerCase();
  }

}
