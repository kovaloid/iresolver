package com.koval.jresolver.processor.rules.core;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.processor.rules.results.RulesResult;


public interface RuleEngine extends AutoCloseable {

  RulesResult execute(Issue actualIssue);
}
