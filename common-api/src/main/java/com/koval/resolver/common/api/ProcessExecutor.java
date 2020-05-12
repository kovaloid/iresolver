package com.koval.resolver.common.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.resolver.common.api.component.processor.IssueProcessor;


public class ProcessExecutor {

  private final List<IssueProcessor> processors = new ArrayList<>();

  public void add(final IssueProcessor processor) {
    processors.add(processor);
  }

  public Collection<IssueAnalysingResult> execute(final Collection<Issue> issues) {
    final Collection<IssueAnalysingResult> results = new ArrayList<>();
    issues.forEach(issue -> results.add(execute(issue)));
    return results;
  }

  private IssueAnalysingResult execute(final Issue issue) {
    final IssueAnalysingResult result = new IssueAnalysingResult();
    processors.forEach(processor -> processor.run(issue, result));
    return result;
  }

  public Collection<IssueAnalysingResult> parallelExecute(final Collection<Issue> issues) {
    final Collection<IssueAnalysingResult> results = new ArrayList<>();
    issues.parallelStream().forEach(issue -> results.add(parallelExecute(issue)));
    return results;
  }

  private IssueAnalysingResult parallelExecute(final Issue issue) {
    final IssueAnalysingResult result = new IssueAnalysingResult();
    processors.parallelStream().forEach(processor -> processor.run(issue, result));
    return result;
  }
}
