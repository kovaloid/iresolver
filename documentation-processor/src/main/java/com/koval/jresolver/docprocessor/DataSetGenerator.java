package com.koval.jresolver.docprocessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.koval.jresolver.processor.similarity.core.dataset.TextDataExtractor;

public class DataSetGenerator {

  public void createDataSet(Map<Integer, String> mapping, String outputFileName) {
    TextDataExtractor textDataExtractor = new TextDataExtractor();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
      for(Map.Entry<Integer, String> entry: mapping.entrySet()) {
        writer.write(entry.getKey().toString());
        writer.write(" | ");
        writer.write(textDataExtractor.simplify(entry.getValue()));
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
