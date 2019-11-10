package com.koval.jresolver.docprocessor.core;

import com.koval.jresolver.docprocessor.bean.DocMetadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class DocMetadataParser {

  public List<DocMetadata> parse() {
    List<DocMetadata> docMetadata = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(DocListParser.class.getResourceAsStream("doc-metadata.txt")))) {
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
