package com.koval.resolver.processor.documentation.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DocFileRepository {

  InputStream getFile(String fileName) throws FileNotFoundException {
    return new FileInputStream(fileName);
  }
}
