package com.koval.jresolver.rules.core;

import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.rules.results.RulesResult;


public interface RuleEngine extends AutoCloseable {

  RulesResult execute(JiraIssue actualIssue);
}
