package com.koval.resolver.processor.documentation.core;

import java.io.*;

public class FileRepository {

  public InputStream readFile(String fileName) throws FileNotFoundException {
    return new FileInputStream(fileName);
  }

  public OutputStream writeToFile(String fileName) throws FileNotFoundException {
    return new FileOutputStream(fileName);
  }

}
