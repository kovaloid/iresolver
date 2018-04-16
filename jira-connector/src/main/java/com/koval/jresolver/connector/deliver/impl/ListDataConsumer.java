package com.koval.jresolver.connector.deliver.impl;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.connector.deliver.DataConsumer;

import java.util.List;


public class ListDataConsumer implements DataConsumer {

  private List<Issue> list;

  public ListDataConsumer(List<Issue> list) {
    this.list = list;
  }

  @Override
  public void consume(Issue issue) {
    list.add(issue);
  }
}
