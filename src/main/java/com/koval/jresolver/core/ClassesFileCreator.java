package com.koval.jresolver.core;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;


public class ClassesFileCreator {

  private final static Logger LOGGER = LoggerFactory.getLogger(WordVectorizer.class);

  private WordVectorizer wordVectorizer;

  public ClassesFileCreator(WordVectorizer wordVectorizer) {
    this.wordVectorizer = wordVectorizer;
  }

  public void createFilesWithPrefix(String fileNamePrefix) {
    Map<Integer, List<String>> classes = wordVectorizer.getClasses();
    for (Map.Entry<Integer, List<String>> c: classes.entrySet()) {
      List<String> list = c.getValue();
      try (OutputStream out = new FileOutputStream(fileNamePrefix + "_" + c.getKey().toString() + ".csv")) {
        for (int i = 0; i < list.size(); i++) {
          IOUtils.write(i + "," + list.get(i) + "\n", out, Charsets.UTF_8);
        }
      } catch (IOException e) {
        LOGGER.error("Could not create file with classes: " + list, e);
      }
    }
  }
}
