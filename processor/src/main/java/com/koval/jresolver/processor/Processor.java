package com.koval.jresolver.processor;

import com.atlassian.jira.rest.client.api.domain.Issue;


public interface Processor {

  void run(Issue issue, IssueProcessingResult result);
}
