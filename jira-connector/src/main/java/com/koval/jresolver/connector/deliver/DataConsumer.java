package com.koval.jresolver.connector.deliver;

import com.koval.jresolver.connector.bean.JiraIssue;


public interface DataConsumer {
  void consume(JiraIssue issue);
}
