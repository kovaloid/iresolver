package com.koval.jresolver;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.util.LaunchUtil;


@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity"})
public final class Launcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

  private static final String CREATE_DATA_SET = "create-data-set";
  private static final String CREATE_VECTOR_MODEL = "create-vector-model";
  private static final String CREATE_DOCUMENTATION_DATA_SET = "create-documentation-data-set";
  private static final String CREATE_DOCUMENTATION_VECTOR_MODEL = "create-documentation-vector-model";
  private static final String CREATE_CONFLUENCE_DATA_SET = "create-confluence-data-set";
  private static final String CREATE_CONFLUENCE_VECTOR_MODEL = "create-confluence-vector-model";
  private static final String CLEAN = "clean";
  private static final String PRINT_FIELDS = "print-fields";
  private static final String TEST_SIMILARITY_PROCESSOR = "test-similarity-processor";
  private static final String HELP_TIP = "Please use the following commands: \n"
      + CREATE_DATA_SET + ", "
      + CREATE_VECTOR_MODEL + ", "
      + CREATE_DOCUMENTATION_DATA_SET + ", "
      + CREATE_DOCUMENTATION_VECTOR_MODEL + ", "
      + CREATE_CONFLUENCE_DATA_SET + ", "
      + CREATE_CONFLUENCE_VECTOR_MODEL + ", "
      + CLEAN + ", "
      + PRINT_FIELDS + ", "
      + TEST_SIMILARITY_PROCESSOR + ".";

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
          LaunchUtil.createSimilarityDataSet();
          break;
        case CREATE_VECTOR_MODEL:
          LOGGER.info("Start creating vector model.");
          LaunchUtil.createSimilarityVectorModel();
          break;
        case CREATE_DOCUMENTATION_DATA_SET:
          LOGGER.info("Start creating documentation data set.");
          LaunchUtil.createDocumentationDataSet();
          break;
        case CREATE_DOCUMENTATION_VECTOR_MODEL:
          LOGGER.info("Start creating documentation vector model.");
          LaunchUtil.createDocumentationVectorModel();
          break;
        case CREATE_CONFLUENCE_DATA_SET:
          LOGGER.info("Start creating documentation data set.");
          LaunchUtil.createConfluenceDataSet();
          break;
        case CREATE_CONFLUENCE_VECTOR_MODEL:
          LOGGER.info("Start creating documentation vector model.");
          LaunchUtil.createConfluenceVectorModel();
          break;
        case CLEAN:
          LOGGER.info("Start cleaning work folder.");
          LaunchUtil.clean();
          break;
        case PRINT_FIELDS:
          LOGGER.info("Start printing fields.");
          LaunchUtil.printFields();
          break;
        case TEST_SIMILARITY_PROCESSOR:
          LOGGER.info("Start testing similarity processor.");
          LaunchUtil.testSimilarityProcessor();
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
