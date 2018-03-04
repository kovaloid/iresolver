package com.koval.jresolver.extraction.deliver;

import com.atlassian.jira.rest.client.domain.Issue;


public interface DataConsumer {
  void consume(Issue issue);
}
