package com.koval.jresolver.connector.jira.core.processing.impl;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.bean.JiraIssue;
import com.koval.jresolver.connector.jira.core.processing.IssuesGenerator;
import com.koval.jresolver.connector.jira.core.processing.IssuesReceiver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ObjectIssuesGenerator implements IssuesGenerator<Collection<Issue>> {

  private IssuesReceiver receiver;
  private Collection<Issue> results;


  public ObjectIssuesGenerator(IssuesReceiver receiver) {
    this.receiver = receiver;
  }

  @Override
  public void launch() {
    results = new ArrayList<>();
    while (receiver.hasNext()) {
      Collection<Issue> issues = receiver.getNextIssues();
      results.addAll(issues);
    }
  }

  @Override
  public Collection<Issue> getResults() {
    return results;
  }
}
