package com.koval.jresolver.connector.jira.deliver;

import com.koval.jresolver.connector.jira.bean.JiraIssue;


public interface DataConsumer {
  void consume(JiraIssue issue);
}
