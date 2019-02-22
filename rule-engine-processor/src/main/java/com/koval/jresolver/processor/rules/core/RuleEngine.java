package com.koval.jresolver.processor.rules.core;

import java.io.Closeable;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.processor.rules.results.RulesResult;


public interface RuleEngine extends Closeable {

  RulesResult execute(Issue actualIssue);
}
