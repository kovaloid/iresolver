package com.koval.jresolver.connector.jira.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.issue.User;
import com.koval.jresolver.common.api.component.connector.IssueTransformer;


public class JiraIssueTransformer implements IssueTransformer<com.atlassian.jira.rest.client.api.domain.Issue> {

  @Override
  public Issue transform(com.atlassian.jira.rest.client.api.domain.Issue originalIssue) {
    Issue transformedIssue = new Issue();
    transformedIssue.setKey(originalIssue.getKey());
    transformedIssue.setSummary(originalIssue.getSummary());
    transformedIssue.setDescription(originalIssue.getDescription());
    transformedIssue.setStatus(originalIssue.getStatus().getName());
    if (originalIssue.getResolution() == null) {
      transformedIssue.setResolution("");
    } else {
      transformedIssue.setResolution(originalIssue.getResolution().getName());
    }
    if (originalIssue.getPriority() == null) {
      transformedIssue.setPriority("");
    } else {
      transformedIssue.setPriority(originalIssue.getPriority().getName());
    }
    transformedIssue.setComments(new ArrayList<>());
    transformedIssue.setAttachments(new ArrayList<>());

    transformedIssue.setLabels(new ArrayList<>());
    transformedIssue.setReporter(new User());
    transformedIssue.setAssignee(new User());
    return transformedIssue;
  }

  @Override
  public List<Issue> transform(Collection<com.atlassian.jira.rest.client.api.domain.Issue> originalIssues) {
    List<Issue> transformedIssues = new ArrayList<>();
    for (com.atlassian.jira.rest.client.api.domain.Issue originalIssue: originalIssues) {
      transformedIssues.add(transform(originalIssue));
    }
    return transformedIssues;
  }
}
