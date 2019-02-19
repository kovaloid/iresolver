package com.koval.jresolver.rules.core;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.rules.results.RulesResult;


public interface RuleEngine extends AutoCloseable {

  RulesResult execute(Issue actualIssue);
}
