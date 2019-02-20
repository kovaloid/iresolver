package com.koval.jresolver.connector.jira.core.processing.generate.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.connector.jira.core.processing.generate.IssuesGenerator;
import com.koval.jresolver.connector.jira.core.processing.receive.IssuesReceiver;


public class CollectionIssuesGenerator implements IssuesGenerator<Collection<Issue>> {

  private final IssuesReceiver receiver;
  private final Collection<Issue> results;

  public CollectionIssuesGenerator(IssuesReceiver receiver) {
    this.receiver = receiver;
    this.results = new ArrayList<>();
  }

  @Override
  public void launch() {
    results.clear();
    while (receiver.hasNextIssues()) {
      Collection<Issue> issues = receiver.getNextIssues();
      results.addAll(issues);
    }
  }

  @Override
  public Collection<Issue> getResults() {
    return results;
  }
}
