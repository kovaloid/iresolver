package com.koval.resolver.processor.rules.core;

import java.io.Closeable;
import java.util.List;

import com.koval.resolver.common.api.model.issue.Issue;


public interface RuleEngine extends Closeable {

  List<String> execute(Issue actualIssue);
}
