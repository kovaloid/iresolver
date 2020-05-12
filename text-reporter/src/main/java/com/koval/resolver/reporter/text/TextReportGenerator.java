package com.koval.resolver.reporter.text;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.issue.User;
import com.koval.resolver.common.api.bean.result.AttachmentResult;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.bean.result.Pair;
import com.koval.resolver.common.api.component.reporter.ReportGenerator;
import com.koval.resolver.common.api.configuration.bean.reporters.TextReporterConfiguration;


public class TextReportGenerator implements ReportGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(TextReportGenerator.class);
  private static final String DASH = " - ";
  private final String outputFile;

  public TextReportGenerator(final TextReporterConfiguration configuration) {
    this.outputFile = configuration.getOutputFile();
  }

  @Override
  public void generate(final List<IssueAnalysingResult> results) {
    final StringBuilder content = new StringBuilder(100);
    for (final IssueAnalysingResult result: results) {
      content.append(result.getOriginalIssue().getKey())
          .append(" : ")
          .append(result.getOriginalIssue().getSummary());

      if (result.getSimilarIssues() != null) {
        fillIssuesProcessorResults(content, result);
      }
      if (result.getProposals() != null) {
        content.append("\nproposals: \n");
        for (final String proposal: result.getProposals()) {
          content.append(proposal)
              .append('\n');
        }
      }
      content.append("\n\n");
    }

    try {
      FileUtils.writeStringToFile(new File(outputFile), content.toString(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      LOGGER.error("Could not save file: " + outputFile, e);
    }
  }

  private void fillIssuesProcessorResults(final StringBuilder content, final IssueAnalysingResult result) {
    content.append("\n\nsimilar issues: \n");
    for (final Pair<Issue, Double> similarIssue: result.getSimilarIssues()) {
      content.append(similarIssue.getEntity().getKey())
          .append(DASH)
          .append(similarIssue.getMetric())
          .append('\n');
    }
    content.append("\nusers: \n");
    for (final Pair<User, Integer> qualifiedUser: result.getQualifiedUsers()) {
      content.append(qualifiedUser.getEntity().getDisplayName())
          .append(DASH)
          .append(qualifiedUser.getEntity().getEmailAddress())
          .append(DASH)
          .append(qualifiedUser.getMetric())
          .append('\n');
    }
    content.append("\nlabels: \n");
    for (final Pair<String, Integer> probableLabel: result.getProbableLabels()) {
      content.append(probableLabel.getEntity())
          .append(DASH)
          .append(probableLabel.getMetric())
          .append('\n');
    }
    content.append("\nattachments: \n");
    for (final AttachmentResult probableAttachment: result.getProbableAttachmentTypes()) {
      content.append(probableAttachment.getExtension())
          .append(DASH)
          .append(probableAttachment.getRank())
          .append(DASH)
          .append(probableAttachment.getType())
          .append(DASH)
          .append(probableAttachment.isPresentInCurrentIssue())
          .append('\n');
    }
  }
}
