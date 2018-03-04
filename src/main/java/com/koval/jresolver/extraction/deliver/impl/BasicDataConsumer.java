package com.koval.jresolver.extraction.deliver.impl;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.extraction.deliver.DataConsumer;
import com.koval.jresolver.extraction.issue.IssueHandler;


public class BasicDataConsumer implements DataConsumer {

  private IssueHandler issueHandler;

  @Override
  public void consume(Issue issue) {



    System.out.println(issue.getSummary());
  }
}
