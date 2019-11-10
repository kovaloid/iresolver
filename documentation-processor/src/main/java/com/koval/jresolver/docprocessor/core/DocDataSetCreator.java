package com.koval.jresolver.docprocessor.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.koval.jresolver.common.api.util.TextUtil;


public class DocDataSetCreator {

  public void create(Map<Integer, String> mapping, File outputFile) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
      for(Map.Entry<Integer, String> entry: mapping.entrySet()) {
        writer.write(entry.getKey().toString());
        writer.write(" | ");
        writer.write(TextUtil.simplify(entry.getValue()));
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
