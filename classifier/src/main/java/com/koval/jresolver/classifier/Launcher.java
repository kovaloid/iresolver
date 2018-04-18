package com.koval.jresolver.classifier;

import java.io.IOException;
import java.net.URISyntaxException;

import com.koval.jresolver.classifier.impl.Doc2vecClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  public static void main(String[] args) throws IOException, URISyntaxException {
	  Classifier classifier = new Doc2vecClassifier();

    if (args.length == 0) {
      LOGGER.warn("No arguments. Please use 'prepare', 'configure' or 'predict'");
    } else if (args.length == 1) {
      switch (args[0]) {
        case "prepare":
          LOGGER.info("Create data set...");
          classifier.prepare();
          break;
        case "configure":
          LOGGER.warn("Train classifier...");
          classifier.configure();
          break;
        case "predict":
          LOGGER.warn("Predict...");
          classifier.getVectorizer().getNearestLabels(args[1], 10);
          break;
        default:
          LOGGER.warn("Wrong arguments. Please use 'prepare', 'configure' or 'predict'");
          break;
      }
    } else {
      LOGGER.warn("Too much arguments. Please use 'prepare', 'configure' or 'predict'");
    }
  }
}
