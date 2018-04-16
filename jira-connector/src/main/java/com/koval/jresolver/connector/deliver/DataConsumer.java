package com.koval.jresolver.connector.deliver;

import com.atlassian.jira.rest.client.domain.Issue;


public interface DataConsumer {
  void consume(Issue issue);
}
