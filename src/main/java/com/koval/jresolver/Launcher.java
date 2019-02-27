package com.koval.jresolver;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.util.LaunchUtil;


public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private static final String CREATE_DATA_SET = "create-data-set";
  private static final String CREATE_VECTOR_MODEL = "create-vector-model";
  private static final String CLEAN = "clean";
  private static final String HELP_TIP = "Please use " + CREATE_DATA_SET + ", " + CREATE_VECTOR_MODEL + " or " + CLEAN + ".";

  private Launcher() {
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      LOGGER.info("Start processing issues.");
      LaunchUtil.run();
    } else if (args.length == 1) {
      switch (args[0]) {
        case CREATE_DATA_SET:
          LOGGER.info("Start creating data set.");
          LaunchUtil.createDataSet();
          break;
        case CREATE_VECTOR_MODEL:
          LOGGER.info("Start creating vector model.");
          LaunchUtil.createVectorModel();
          break;
        case CLEAN:
          LOGGER.info("Start cleaning work folder.");
          LaunchUtil.clean();
          break;
        default:
          LOGGER.warn("Wrong arguments.");
          LOGGER.warn(HELP_TIP);
          break;
      }
    } else {
      LOGGER.warn("Too much arguments.");
      LOGGER.warn(HELP_TIP);
    }

    LOGGER.info("Press any key to continue . . . ");
    try {
      System.in.read();
    } catch (IOException ignored) {
    }
  }
}
