package com.koval.jresolver.processor.similarity.core.dataset;

import com.atlassian.jira.rest.client.api.domain.Issue;


public class TextDataExtractor {

  public String extract(Issue issue) {
    StringBuilder textData = new StringBuilder(100);
    if (issue.getSummary() != null && !issue.getSummary().isEmpty()) {
      textData
          .append(simplify(issue.getSummary()))
          .append(' ');
    }
    if (issue.getDescription() != null && !issue.getDescription().isEmpty()) {
      textData
          .append(simplify(issue.getDescription()))
          .append(' ');
    }
    return textData.toString().trim();
  }

  private String simplify(String text) {
    return text
        .trim()
        .replaceAll("[^A-Za-z0-9]", " ")
        .replaceAll(" +", " ");
  }
}
