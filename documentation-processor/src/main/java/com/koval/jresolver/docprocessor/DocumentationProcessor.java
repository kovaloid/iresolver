package com.koval.jresolver.docprocessor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

import com.koval.jresolver.common.api.doc2vec.Doc2VecProperties;
import com.koval.jresolver.common.api.doc2vec.VectorModel;
import com.koval.jresolver.common.api.doc2vec.VectorModelCreator;
import com.koval.jresolver.common.api.doc2vec.VectorModelSerializer;
import com.koval.jresolver.docprocessor.configuration.DocumentationProcessorProperties;
import com.koval.jresolver.docprocessor.core.DataSetGenerator;
import com.koval.jresolver.docprocessor.core.DocTypeDetector;
import com.koval.jresolver.docprocessor.core.FileParser;
import org.apache.tika.mime.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentationProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentationProcessor.class);

  private static final File workFolder = new File("src/main/resources");
  private static final String outputFileName = "../output/pdf-data-set.txt";

  public static void main(String[] args) {
    DocumentationProcessor doc = new DocumentationProcessor();
    doc.createDataSet();
    doc.createVectorModel();
  }

  private void createDataSet() {
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

  private void createVectorModel() {
    DocumentationProcessorProperties properties = new DocumentationProcessorProperties();
    VectorModelCreator vectorModelCreator = new VectorModelCreator(properties);
    File dataSetFile = new File(properties.getWorkFolder(), properties.getDataSetFileName());
    try {
      VectorModel vectorModel = vectorModelCreator.createFromFile(dataSetFile);
      VectorModelSerializer vectorModelSerializer = new VectorModelSerializer(properties);
      vectorModelSerializer.serialize(vectorModel);
    } catch (IOException e) {
      LOGGER.error("Could not create vector model file.", e);
    }
  }

}
