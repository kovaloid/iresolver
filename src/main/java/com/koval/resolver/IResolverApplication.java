package com.koval.resolver;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.configuration.Configuration;
import com.koval.resolver.common.api.configuration.ConfigurationManager;
import com.koval.resolver.core.Launcher;


@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity"})
public final class IResolverApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(IResolverApplication.class);

  private static final String CREATE_ISSUES_DATA_SET = "create-issues-data-set";
  private static final String CREATE_ISSUES_VECTOR_MODEL = "create-issues-vector-model";
  private static final String CREATE_GRANULAR_ISSUES_DATA_SETS = "create-granular-issues-data-sets";
  private static final String CREATE_GRANULAR_ISSUES_VECTOR_MODELS = "create-granular-issues-vector-models";
  private static final String CREATE_DOCUMENTATION_DATA_SET = "create-documentation-data-set";
  private static final String CREATE_DOCUMENTATION_VECTOR_MODEL = "create-documentation-vector-model";
  private static final String CREATE_CONFLUENCE_DATA_SET = "create-confluence-data-set";
  private static final String CREATE_CONFLUENCE_VECTOR_MODEL = "create-confluence-vector-model";
  private static final String PRINT_ISSUE_FIELDS = "print-issue-fields";
  private static final String TEST_SIMILARITY_PROCESSOR = "test-similarity-processor";
  private static final String RUN_UI = "run-ui";
  private static final String HELP_TIP = "Please use the following commands: \n"
      + CREATE_ISSUES_DATA_SET + ", "
      + CREATE_ISSUES_VECTOR_MODEL + ", "
      + CREATE_GRANULAR_ISSUES_DATA_SETS + ", "
      + CREATE_GRANULAR_ISSUES_VECTOR_MODELS + ", "
      + CREATE_DOCUMENTATION_DATA_SET + ", "
      + CREATE_DOCUMENTATION_VECTOR_MODEL + ", "
      + CREATE_CONFLUENCE_DATA_SET + ", "
      + CREATE_CONFLUENCE_VECTOR_MODEL + ", "
      + PRINT_ISSUE_FIELDS + ", "
      + RUN_UI + ", "
      + TEST_SIMILARITY_PROCESSOR + ".";

  private IResolverApplication() {
  }

  public static void main(String[] args) {
    Configuration configuration = ConfigurationManager.getConfiguration();
    Launcher launcher = new Launcher(configuration);

    if (args.length == 0) {
      LOGGER.info("Start processing issues.");
      launcher.run();
    } else if (args.length == 1) {
      switch (args[0]) {
        case CREATE_ISSUES_DATA_SET:
          LOGGER.info("Start creating issues data set.");
          launcher.createIssuesDataSet();
          break;
        case CREATE_ISSUES_VECTOR_MODEL:
          LOGGER.info("Start creating issues vector model.");
          launcher.createIssuesVectorModel();
          break;
        case CREATE_GRANULAR_ISSUES_DATA_SETS:
          LOGGER.info("Start creating granular issues data sets.");
          launcher.createGranularIssuesDataSets();
          break;
        case CREATE_GRANULAR_ISSUES_VECTOR_MODELS:
          LOGGER.info("Start creating granular issues vector models.");
          launcher.createGranularIssuesVectorModels();
          break;
        case CREATE_DOCUMENTATION_DATA_SET:
          LOGGER.info("Start creating documentation data set.");
          launcher.createDocumentationDataSet();
          break;
        case CREATE_DOCUMENTATION_VECTOR_MODEL:
          LOGGER.info("Start creating documentation vector model.");
          launcher.createDocumentationVectorModel();
          break;
        case CREATE_CONFLUENCE_DATA_SET:
          LOGGER.info("Start creating Confluence data set.");
          launcher.createConfluenceDataSet();
          break;
        case CREATE_CONFLUENCE_VECTOR_MODEL:
          LOGGER.info("Start creating Confluence vector model.");
          launcher.createConfluenceVectorModel();
          break;
        case PRINT_ISSUE_FIELDS:
          LOGGER.info("Start printing issue fields.");
          launcher.printIssueFields();
          break;
        case RUN_UI:
          LOGGER.info("Start configuration wizard.");
          launcher.runUI();
          break;
        case TEST_SIMILARITY_PROCESSOR:
          LOGGER.info("Start testing similarity processor.");
          launcher.testSimilarityProcessor();
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
