package com.example.employeehandbook.ingestion;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceReader {

  public List<Document> read(Resource resource) {

    String filename = resource.getFilename();

    if (filename == null) {
      throw new IllegalArgumentException("Resource must have a filename");
    }

    DocumentReader reader = createReader(resource, filename);

    return reader.get();
  }

  private DocumentReader createReader(Resource resource, String filename) {

    String lowername = filename.toLowerCase();
    if (lowername.endsWith("pdf")) {

      return new PagePdfDocumentReader(resource);
    }

    return new TikaDocumentReader(resource);
  }

}
