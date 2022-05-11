package com.koval.resolver.processor.issues.granular.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.model.issue.Issue;
import com.koval.resolver.common.api.component.connector.IssueReceiver;
import com.koval.resolver.common.api.configuration.component.processors.GranularIssuesProcessorConfiguration;
import com.koval.resolver.common.api.constant.IssueParts;
import com.koval.resolver.common.api.util.TextUtil;


public class GranularIssuesDataSetsCreator {

  private static final Logger LOGGER = LoggerFactory.getLogger(GranularIssuesDataSetsCreator.class);
  private static final String SEPARATOR = "|";

  private final IssueReceiver receiver;
  private final GranularIssuesProcessorConfiguration properties;

  public GranularIssuesDataSetsCreator(final IssueReceiver receiver, final GranularIssuesProcessorConfiguration properties) {
    this.receiver = receiver;
    this.properties = properties;
  }

  @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity",
      "PMD.StdCyclomaticComplexity", "PMD.NPathComplexity"})
  public void create() throws IOException {
    PrintWriter summaryWriter = null;
    PrintWriter descriptionWriter = null;
    PrintWriter commentsWriter = null;
    final List<String> affectedIssueParts = properties.getAffectedIssueParts();
    try {
      if (affectedIssueParts.contains(IssueParts.SUMMARY.getContent())) {
        summaryWriter = getDataSetWriter(properties.getSummaryDataSetFile());
      }
      if (affectedIssueParts.contains(IssueParts.DESCRIPTION.getContent())) {
        descriptionWriter = getDataSetWriter(properties.getDescriptionDataSetFile());
      }
      if (affectedIssueParts.contains(IssueParts.COMMENTS.getContent())) {
        commentsWriter = getDataSetWriter(properties.getCommentsDataSetFile());
      }

      while (receiver.hasNextIssues()) {
        final Collection<Issue> issues = receiver.getNextIssues();
        for (final Issue issue : issues) {
          if (summaryWriter != null) {
            summaryWriter.print(issue.getKey());
            summaryWriter.print(SEPARATOR);
            summaryWriter.println(TextUtil.simplify(issue.getSummary()));
          }
          if (descriptionWriter != null) {
            descriptionWriter.print(issue.getKey());
            descriptionWriter.print(SEPARATOR);
            String descriptionText = issue.getDescription();
            if (descriptionText == null) {
                descriptionWriter.println("No description");
            } else {
                descriptionWriter.println(TextUtil.simplify(descriptionText));
            }
          }
          if (commentsWriter != null && !issue.getComments().isEmpty()) {
            commentsWriter.print(issue.getKey());
            commentsWriter.print(SEPARATOR);
            final StringBuilder comments = new StringBuilder();
            issue.getComments().forEach(comment -> comments.append(comment.getBody()));
            commentsWriter.println(TextUtil.simplify(comments.toString()));
          }
          LOGGER.info("Issue with key {} was added to data sets", issue.getKey());
        }
        if (summaryWriter != null) {
          summaryWriter.flush();
        }
        if (descriptionWriter != null) {
          descriptionWriter.flush();
        }
        if (commentsWriter != null) {
          commentsWriter.flush();
        }
      }
    } finally {
      if (summaryWriter != null) {
        summaryWriter.close();
        LOGGER.info("Summary data set file was created");
      }
      if (descriptionWriter != null) {
        descriptionWriter.close();
        LOGGER.info("Description data set file was created");
      }
      if (commentsWriter != null) {
        commentsWriter.close();
        LOGGER.info("Comments data set file was created");
      }
    }
  }

  private PrintWriter getDataSetWriter(final String dataSetPath) throws IOException {
    final File dataSetFile = new File(dataSetPath);
    FileUtils.forceMkdir(dataSetFile.getParentFile());
    LOGGER.info("Folder to store data set file created: {}", dataSetFile.getParentFile().getCanonicalPath());
    LOGGER.info("Start creating data set file: {}", dataSetFile.getName());
    return new PrintWriter(dataSetFile, StandardCharsets.UTF_8.name());
  }
}
