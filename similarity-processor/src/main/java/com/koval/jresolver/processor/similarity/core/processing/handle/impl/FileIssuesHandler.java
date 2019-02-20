package com.koval.jresolver.connector.jira.core.processing.handle.impl;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.core.processing.handle.IssuesHandler;


public class FileIssuesHandler implements IssuesHandler<String, Issue> {

  public String extractTextData(Issue issue) {
    String text = "";
    if (issue.getDescription() != null) {
      text = issue.getSummary() + " " + issue.getDescription().trim().replaceAll("[^A-Za-z0-9]", " ").replaceAll(" +", " ");
    }
    return text;
  }

  @Override
  public String transform(Issue issue) {
    return extractTextData(issue);
  }
}
