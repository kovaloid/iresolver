package com.koval.jresolver.processor.rules.core;

import java.io.Closeable;
import java.util.List;

import com.koval.jresolver.common.api.bean.issue.Issue;


public interface RuleEngine extends Closeable {

  List<String> execute(Issue actualIssue);
}
