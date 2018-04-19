package com.koval.jresolver.connector.deliver.impl;

import java.util.ArrayList;
import java.util.List;

import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.deliver.DataConsumer;


public class ListDataConsumer implements DataConsumer {

  private final List<JiraIssue> issueList;

  public ListDataConsumer() {
    this.issueList = new ArrayList<>();
  }

  public ListDataConsumer(List<JiraIssue> issueList) {
    this.issueList = issueList;
  }

  @Override
  public void consume(JiraIssue issue) {
    issueList.add(issue);
  }

  public List<JiraIssue> getIssues() {
    return issueList;
  }
}
