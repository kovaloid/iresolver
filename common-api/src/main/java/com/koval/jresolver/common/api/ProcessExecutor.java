package com.koval.jresolver.common.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;
import com.koval.jresolver.common.api.component.processor.IssueProcessor;


public class ProcessExecutor {

  private final List<IssueProcessor> processors = new ArrayList<>();

  public ProcessExecutor add(IssueProcessor processor) {
    processors.add(processor);
    return this;
  }

  public IssueAnalysingResult execute(Issue issue) {
    IssueAnalysingResult result = new IssueAnalysingResult();
    processors.forEach(processor -> processor.run(issue, result));
    return result;
  }

  public Collection<IssueAnalysingResult> execute(Collection<Issue> issues) {
    Collection<IssueAnalysingResult> results = new ArrayList<>();
    issues.forEach(issue -> results.add(execute(issue)));
    return results;
  }

  public IssueAnalysingResult parallelExecute(Issue issue) {
    IssueAnalysingResult result = new IssueAnalysingResult();
    processors.parallelStream().forEach(processor -> {
      processor.run(issue, result);
    });
    return result;
  }

  public Collection<IssueAnalysingResult> parallelExecute(Collection<Issue> issues) {
    Collection<IssueAnalysingResult> results = new ArrayList<>();
    issues.parallelStream().forEach(issue -> results.add(parallelExecute(issue)));
    return results;
  }
}
