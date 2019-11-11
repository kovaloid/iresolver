package com.koval.jresolver.docprocessor.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.koval.jresolver.docprocessor.bean.DocFile;
import com.koval.jresolver.docprocessor.bean.DocMetadata;


public class DocOutputFilesParser {

  /*
  private DocumentationProcessorProperties properties;

  public DocOutputFilesParser(DocumentationProcessorProperties properties) {
    this.properties = properties;
  }
  */

  public List<DocFile> parseDocumentationFilesList() {
    List<DocFile> docFiles = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(DocOutputFilesParser.class.getResourceAsStream("doc-list.txt")))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(" ");
        int fileIndex = Integer.valueOf(split[0]);
        String fileName = split[1];
        docFiles.add(new DocFile(fileIndex, fileName));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return docFiles;
  }

  public List<DocMetadata> parseDocumentationMetadata() {
    List<DocMetadata> docMetadata = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(DocOutputFilesParser.class.getResourceAsStream("doc-metadata.txt")))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(" ");
        docMetadata.add(new DocMetadata(split[0], Integer.valueOf(split[1]), Integer.valueOf(split[2])));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return docMetadata;
  }
}
