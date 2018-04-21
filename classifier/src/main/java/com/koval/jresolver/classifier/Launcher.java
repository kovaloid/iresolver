package com.koval.jresolver.classifier;

import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.core.impl.DocClassifier;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private Launcher() {
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    ClassifierProperties classifierProperties = new ClassifierProperties("classifier.properties");
    Classifier classifier = new DocClassifier(classifierProperties);

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
        default:
          LOGGER.warn("Wrong arguments. Please use 'prepare', 'configure' or 'predict'");
          break;
      }
    } else if (args.length == 2) {
      if (args[0].equals("predict")) {
        LOGGER.warn("Predict...");
        classifier.getVectorizer().getNearestLabels(args[1], 10);
      } else {
        LOGGER.warn("Try to type 'predict' and second argument");
      }
    } else {
      LOGGER.warn("Too much arguments. Please use 'prepare', 'configure' or 'predict'");
    }
  }
}
