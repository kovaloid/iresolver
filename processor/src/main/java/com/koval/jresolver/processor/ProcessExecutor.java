package com.koval.jresolver.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.processor.api.Processor;
import com.koval.jresolver.processor.result.IssueProcessingResult;


public class ProcessExecutor {

  private final List<Processor> processors = new ArrayList<>();

  public ProcessExecutor add(Processor processor) {
    processors.add(processor);
    return this;
  }

  public IssueProcessingResult execute(Issue issue) {
    IssueProcessingResult result = new IssueProcessingResult();
    processors.forEach(processor -> processor.run(issue, result));
    return result;
  }

  public Collection<IssueProcessingResult> execute(Collection<Issue> issues) {
    Collection<IssueProcessingResult> results = new ArrayList<>();
    issues.forEach(issue -> results.add(execute(issue)));
    return results;
  }

  public IssueProcessingResult parallelExecute(Issue issue) {
    IssueProcessingResult result = new IssueProcessingResult();
    processors.parallelStream().forEach(processor -> {
      processor.run(issue, result);
    });
    return result;
  }

  public Collection<IssueProcessingResult> parallelExecute(Collection<Issue> issues) {
    Collection<IssueProcessingResult> results = new ArrayList<>();
    issues.parallelStream().forEach(issue -> results.add(parallelExecute(issue)));
    return results;
  }

  private void launchThreads(List<Thread> threads) throws InterruptedException {
    for (Thread thread: threads) {
      thread.start();
    }
    for (Thread thread: threads) {
      thread.join();
    }
  }
}
