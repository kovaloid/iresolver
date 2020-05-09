package com.koval.resolver.processor.documentation.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileRepository {

  public InputStream readFile(String fileName) throws FileNotFoundException {
    return new FileInputStream(fileName);
  }
}
