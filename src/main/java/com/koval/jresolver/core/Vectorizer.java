package com.koval.jresolver.core;

import org.deeplearning4j.text.sentenceiterator.SentenceIterator;

import java.io.File;
import java.io.InputStream;


public interface Vectorizer {

  void createFromFile(File inputFile);

  void createFromInputStream(InputStream inputStream);

  void save(String pathToSerializedFile);

  void load(String pathToSerializedFile);
}
