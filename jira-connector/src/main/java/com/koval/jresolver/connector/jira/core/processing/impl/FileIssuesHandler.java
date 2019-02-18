package com.koval.jresolver.connector.jira.core.processing.impl;

import com.atlassian.jira.rest.client.api.domain.Issue;

public class FileIssuesHandler {
  public String extractTextData(Issue issue) {
    String text = "";
    if (issue.getDescription() != null) {
      text = issue.getSummary() + " " + issue.getDescription().trim().replaceAll("[^A-Za-z0-9]", " ").replaceAll(" +", " ");
    }
    return text;
  }
}
