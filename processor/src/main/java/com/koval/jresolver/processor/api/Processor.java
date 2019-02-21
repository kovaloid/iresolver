package com.koval.jresolver.processor.api;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.processor.result.IssueProcessingResult;


public interface Processor {

  void run(Issue issue, IssueProcessingResult result);
}
