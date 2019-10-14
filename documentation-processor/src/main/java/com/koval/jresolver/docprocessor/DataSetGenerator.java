package com.koval.jresolver.docprocessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class DataSetGenerator {

  public void createDataSet(Map<Integer, String> mapping) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("pdf-data-set.txt"))) {
      for(Map.Entry<Integer, String> entry: mapping.entrySet()) {
        writer.write(entry.getKey().toString());
        writer.write(" | ");
        writer.write(entry.getValue().replaceAll("\n", "").replaceAll("\r", ""));
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
