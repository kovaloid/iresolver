package com.koval.jresolver.processor.similarity.test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareListSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareSentenceIterator;
import org.nd4j.linalg.ops.transforms.Transforms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;
import com.koval.jresolver.processor.similarity.core.model.VectorModel;
import com.koval.jresolver.processor.similarity.core.model.VectorModelCreator;


public class TestSimilarityProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestSimilarityProcessor.class);

  public void test() throws IOException {
    SimilarityProcessorProperties properties = new SimilarityProcessorProperties();
    VectorModelCreator vectorModelCreator = new VectorModelCreator(properties);

    File dataSetStream = new File(properties.getWorkFolder(), properties.getDataSetFileName());
    InputStream inputStream = new FileInputStream(dataSetStream);

    //Path path = Paths.get(dataSetStream.getCanonicalPath());
    LabelAwareSentenceIterator iterator = new LabelAwareListSentenceIterator(inputStream, "|", 0, 1);
    int issueTotal = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.nextSentence());
      issueTotal++;
    }
    LOGGER.info("" + issueTotal);

    double trainCount = issueTotal * 0.9;
    int trainSetCount = (int)trainCount;
    int testSetCount = issueTotal - trainSetCount;

    LOGGER.info("Sentences: " + issueTotal);
    LOGGER.info("Train Set: " + trainSetCount);
    LOGGER.info("Test Set: " + testSetCount);

    File trainSetFile = new File(properties.getWorkFolder(), properties.getTrainSetFileName());
    File testSetFile = new File(properties.getWorkFolder(), properties.getTestSetFileName());
    //FileUtils.forceMkdir(dataSetFile.getParentFile());
    SentenceIterator trainIterator = new LineSentenceIterator(dataSetStream);
    int tempCount = 0;
    try (PrintWriter output = new PrintWriter(trainSetFile, StandardCharsets.UTF_8.name())) {
      while (trainIterator.hasNext() && tempCount < trainSetCount) {
        output.println(trainIterator.nextSentence());
        tempCount++;
        output.flush();
      }
    }
    LOGGER.info("Train data set file was created: {}" + trainSetFile.getCanonicalPath());
    try (PrintWriter outputTest = new PrintWriter(testSetFile, StandardCharsets.UTF_8.name())) {
      while (trainIterator.hasNext() && tempCount < issueTotal) {
        outputTest.println(trainIterator.nextSentence());
        outputTest.flush();
        tempCount++;
      }
    }
    iterator.finish();
    LOGGER.info("Test data set file was created: {}", testSetFile.getCanonicalPath());
    try {
      VectorModel vectorModel = vectorModelCreator.createFromFile(trainSetFile);
      int errorCounter = 0;
      int totalCounter = 0;
      double errorRate;

      InputStream inputStreamTest = FileUtils.openInputStream(testSetFile);
      InputStream inputStreamTrain = FileUtils.openInputStream(trainSetFile);
      LabelAwareSentenceIterator iteratorTest = new LabelAwareListSentenceIterator(inputStreamTest, "|", 0, 1);
      LabelAwareSentenceIterator iteratorTrain = new LabelAwareListSentenceIterator(inputStreamTrain, "|", 0, 1);
      while (iteratorTest.hasNext()) {
        String currentSentence = iteratorTest.nextSentence();
        String currentLabel = iteratorTest.currentLabel();

        String nearestLabel = vectorModel.getNearestLabels(currentSentence, 1).iterator().next();

        LabelledDocument currentVector = new LabelledDocument();
        currentVector.setContent(currentSentence);
        currentVector.addLabel(currentLabel);

        while (iteratorTrain.hasNext()) {
          String currentSentenceTrain = iteratorTrain.nextSentence();
          String currentLabelTrain = iteratorTrain.currentLabel();
          if (currentLabelTrain.equals(nearestLabel)) {
            LabelledDocument nearestVector = new LabelledDocument();
            nearestVector.setContent(currentSentenceTrain);
            nearestVector.addLabel(currentLabelTrain);
            double similarity = Transforms.cosineSim(vectorModel.getParagraphVectors().inferVector(currentVector), vectorModel.getParagraphVectors().inferVector(nearestVector));
            errorCounter = similarity(errorCounter, similarity);
            LOGGER.info("Similarity: " + similarity);
            break;
          }
        }

        totalCounter++;
      }
      errorRate = (double)errorCounter / totalCounter * 100;
      LOGGER.info("Current error rate: {}/{} ({}%)", errorCounter, totalCounter, String.format("%.4f", errorRate));
    } catch (IOException e) {
      LOGGER.error("Could not create vector model file.", e);
    }

  }

  public int similarity(int errorCounter, double similarity) {
    int errorCounterSim = errorCounter;
    if (similarity < 0.8) {
      LOGGER.info("{} : Similarity between text and label less than 80%");
      errorCounterSim++;
      return errorCounterSim;
    }
    return errorCounterSim;
  }
}
