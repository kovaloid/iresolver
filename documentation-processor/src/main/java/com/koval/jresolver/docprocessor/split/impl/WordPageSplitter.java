package com.koval.jresolver.docprocessor.split.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.aspose.words.Document;
import com.aspose.words.NodeType;
import com.aspose.words.Paragraph;
import com.aspose.words.SaveFormat;

import com.koval.jresolver.docprocessor.split.PageSplitter;


public class WordPageSplitter implements PageSplitter {

  @Override
  public Map<Integer, String> getMapping(InputStream input) throws IOException {
    Map<Integer, String> result = new HashMap<>();
    try {
      Document doc = new Document(input);
      for (Object node : doc.getChildNodes(NodeType.PARAGRAPH, true)) {
        Paragraph paragraph = (Paragraph)node;
        String text = paragraph.toString(SaveFormat.TEXT);
        System.out.println("paragraph> " + text);
      }
    } catch (Exception e) {
      throw new IOException("Could not read MS Word file", e);
    }
    return result;
  }
}
