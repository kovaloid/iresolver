package com.koval.jresolver.connector.jira.core.impl;

import com.koval.jresolver.connector.api.Issue;


public class JiraIssueTransformer {

  public static Issue convert(com.atlassian.jira.rest.client.api.domain.Issue initial) {
    Issue converted = new Issue();
    converted.setSummary(initial.getSummary());
    converted.setDescription(initial.getDescription());
    converted.setStatus(initial.getStatus().getName());
    if (initial.getResolution() == null) {
      converted.setResolution("");
    } else {
      converted.setResolution(initial.getResolution().getName());
    }
    if (initial.getPriority() == null) {
      converted.setPriority("");
    } else {
      converted.setPriority(initial.getPriority().getName());
    }
    return converted;
  }
}
