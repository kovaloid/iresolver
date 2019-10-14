package com.koval.jresolver.docprocessor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class DocumentationProcessor {
  public static void main(String[] args) {
    PdfFileParser parser = new PdfFileParser();
    DataSetGenerator generator = new DataSetGenerator();
    try (InputStream input = DocumentationProcessor.class.getResourceAsStream("/test.pdf")) {
      if (input == null) {
        System.out.println("No such file");
      }

      Map<Integer, String> result = parser.getMapping(input);
      System.out.println(result);

      generator.createDataSet(result);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
