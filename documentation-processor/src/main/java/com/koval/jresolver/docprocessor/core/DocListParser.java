package com.koval.jresolver.docprocessor.core;

import com.koval.jresolver.docprocessor.bean.DocFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class DocListParser {

  public List<DocFile> parse() {
    List<DocFile> docFiles = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(DocListParser.class.getResourceAsStream("doc-list.txt")))) {
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
}
