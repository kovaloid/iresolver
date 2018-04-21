package com.koval.jresolver.classifier.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;


public interface Vectorizer {

  void createFromFile(File inputFile);

  void createFromInputStream(InputStream inputStream) throws IOException;

  void save(String pathToSerializedFile);

  void load(String pathToSerializedFile);

  Collection<String> getNearestLabels(String rawText, int topN);
}
