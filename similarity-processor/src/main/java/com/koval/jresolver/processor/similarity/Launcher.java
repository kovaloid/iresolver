package com.koval.jresolver.processor.similarity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.processor.similarity.configuration.Doc2VecProperties;
import com.koval.jresolver.processor.similarity.core.SimilarityProcessor;
import com.koval.jresolver.processor.similarity.results.SimilarityResult;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private Launcher() {
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    Doc2VecProperties doc2vecProperties = new Doc2VecProperties();
    SimilarityProcessor similarityProcessor = new SimilarityProcessor(doc2vecProperties);
    if (args.length == 0) {
      LOGGER.warn("No arguments. Please use 'train' or 'predict'.");
    } else if (args.length == 1) {
      switch (args[0]) {
        case "train":
          LOGGER.info("Data set creation phase started.");
          similarityProcessor.train();
          break;
        case "predict":
          LOGGER.info("Predict phase started.");
          List<SimilarityResult> results = similarityProcessor.predict();
          for (SimilarityResult result: results) {
            System.out.println(result.getKey() + "     " + result.getIssues());
          }
          break;
        default:
          LOGGER.warn("Wrong arguments. Please use 'train' or 'predict'.");
          break;
      }
    } else {
      LOGGER.warn("Too much arguments. Please use 'train' or 'predict'.");
    }
  }
}
