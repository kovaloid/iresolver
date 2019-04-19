package com.koval.jresolver.reporter.text;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.bean.issue.Attachment;
import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.issue.User;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.bean.result.Pair;
import com.koval.jresolver.common.api.component.reporter.ReportGenerator;


public class TextReportGenerator implements ReportGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(TextReportGenerator.class);
  private static final String DASH = " - ";

  @Override
  public void generate(List<IssueAnalysingResult> results) {
    StringBuilder content = new StringBuilder(100);
    for (IssueAnalysingResult result: results) {
      content.append(result.getOriginalIssue().getKey())
          .append(" : ")
          .append(result.getOriginalIssue().getSummary());

      if (result.getSimilarIssues() != null) {
        content.append("\n\nsimilar issues: \n");
        for (Pair<Issue, Double> similarIssue: result.getSimilarIssues()) {
          content.append(similarIssue.getEntity().getKey())
              .append(DASH)
              .append(similarIssue.getMetric())
              .append('\n');
        }
        content.append("\nusers: \n");
        for (Pair<User, Integer> qualifiedUser: result.getQualifiedUsers()) {
          content.append(qualifiedUser.getEntity().getDisplayName())
              .append(DASH)
              .append(qualifiedUser.getMetric())
              .append('\n');
        }
        content.append("\nlabels: \n");
        for (Pair<String, Integer> probableLabel: result.getProbableLabels()) {
          content.append(probableLabel.getEntity())
              .append(DASH)
              .append(probableLabel.getMetric())
              .append('\n');
        }
        content.append("\nattachments: \n");
        for (Pair<Attachment, Integer> probableAttachment: result.getProbableAttachments()) {
          content.append(probableAttachment.getEntity().getFileName())
              .append(DASH)
              .append(probableAttachment.getMetric())
              .append('\n');
        }
      }
      if (result.getProposals() != null) {
        content.append("\nproposals: \n");
        for (String proposal: result.getProposals()) {
          content.append(proposal)
              .append('\n');
        }
      }
      content.append("\n\n");
    }

    try {
      FileUtils.writeStringToFile(new File("../output/report.txt"), content.toString(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      LOGGER.error("Could not save report.txt file", e);
    }
  }
}
