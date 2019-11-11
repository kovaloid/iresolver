package com.koval.jresolver.processor.similarity.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareListSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareSentenceIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.doc2vec.VectorModel;
import com.koval.jresolver.common.api.doc2vec.VectorModelSerializer;
import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;


public class TestSimilarityProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestSimilarityProcessor.class);

  public void test() throws IOException {
    SimilarityProcessorProperties properties = new SimilarityProcessorProperties();
    VectorModelSerializer vectorModelSerializer = new VectorModelSerializer(properties);

    File vectorModelFile = new File(properties.getWorkFolder(), properties.getVectorModelFileName());
    VectorModel vectorModel = vectorModelSerializer.deserialize(vectorModelFile);

    int errorCounter = 0;
    int totalCounter = 0;
    double errorRate;

    try (InputStream inputStream = FileUtils.openInputStream(new File(properties.getWorkFolder(), properties.getDataSetFileName()))) {
      LabelAwareSentenceIterator iterator = new LabelAwareListSentenceIterator(inputStream, "|", 0, 1);
      while (iterator.hasNext()) {
        String currentSentence = iterator.nextSentence();
        String currentLabel = iterator.currentLabel();

        String nearestLabel = vectorModel.getNearestLabels(currentSentence, 1).iterator().next();
        if (!nearestLabel.equalsIgnoreCase(currentLabel)) {
          LOGGER.info("{} : Impossible to find a correct label for the corresponding text!", currentLabel);
          errorCounter++;
        }

        /*
        double similarity = vectorModel.getParagraphVectors().similarityToLabel(currentSentence, currentLabel);
        if (similarity < 0.8) {
          LOGGER.info("{} : Similarity between text and label less than 80%", currentLabel);
          errorCounter++;
        }
        */

        totalCounter++;
        errorRate = (double)errorCounter / totalCounter * 100;
        LOGGER.info("Current error rate: {}/{} ({}%)", errorCounter, totalCounter, String.format("%.4f", errorRate));
      }
    }
  }
}
