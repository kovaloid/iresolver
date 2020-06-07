package com.koval.resolver.processor.issues.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareListSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareSentenceIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.configuration.ConfigurationManager;
import com.koval.resolver.common.api.configuration.bean.processors.IssuesProcessorConfiguration;
import com.koval.resolver.common.api.doc2vec.VectorModel;
import com.koval.resolver.common.api.doc2vec.VectorModelSerializer;

public class TestSimilarityProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestSimilarityProcessor.class);

  public void test() throws IOException {
    final IssuesProcessorConfiguration properties = ConfigurationManager.getConfiguration().getProcessors().getIssues();
    final VectorModelSerializer vectorModelSerializer = new VectorModelSerializer();

    final File vectorModelFile = new File(properties.getVectorModelFile());
    final VectorModel vectorModel = vectorModelSerializer.deserialize(vectorModelFile, "English");

    int errorCounter = 0;
    int totalCounter = 0;
    double errorRate;

    try (InputStream inputStream = FileUtils.openInputStream(new File(properties.getDataSetFile()))) {
      final LabelAwareSentenceIterator iterator = new LabelAwareListSentenceIterator(inputStream,
                                                                                     "|",
                                                                                     0,
                                                                                     1);
      while (iterator.hasNext()) {
        final String currentSentence = iterator.nextSentence();
        final String currentLabel = iterator.currentLabel();

        final String nearestLabel = vectorModel.getNearestLabels(currentSentence, 1).iterator().next();
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
