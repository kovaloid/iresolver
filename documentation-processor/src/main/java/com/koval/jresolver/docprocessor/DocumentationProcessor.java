package com.koval.jresolver.docprocessor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

import org.apache.tika.mime.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentationProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentationProcessor.class);

  private static final File workFolder = new File("src/main/resources");
  private static final String outputFileName = "../output/pdf-data-set.txt";

  public static void main(String[] args) {
    DataSetGenerator dataSetGenerator = new DataSetGenerator();
    DocTypeDetector docTypeDetector = new DocTypeDetector();

    for (final File fileEntry: Objects.requireNonNull(workFolder.listFiles())) {
      if (fileEntry.isFile()) {
        try (InputStream inputFileStream = new BufferedInputStream(new FileInputStream(fileEntry))) {
          MediaType mediaType = docTypeDetector.detectType(inputFileStream, fileEntry.getName());
          if (docTypeDetector.isTypeSupported(mediaType)) {
            FileParser fileParser = docTypeDetector.getFileParser(mediaType);
            Map<Integer, String> result = fileParser.getMapping(inputFileStream);
            dataSetGenerator.createDataSet(result, outputFileName);
          }
        } catch (FileNotFoundException e) {
          LOGGER.error("Could not find work folder", e);
        } catch (IOException e) {
          LOGGER.error("Could not read files from work folder", e);
        }
      }
    }
  }

}
