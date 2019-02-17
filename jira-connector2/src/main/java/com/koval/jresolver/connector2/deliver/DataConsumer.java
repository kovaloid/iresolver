package com.koval.jresolver.connector2.deliver;

import com.koval.jresolver.connector2.bean.JiraIssue;


public interface DataConsumer {
  void consume(JiraIssue issue);
}
