package com.koval.resolver.common.api.component.processor;

import com.koval.resolver.common.api.model.issue.Issue;
import com.koval.resolver.common.api.model.result.IssueAnalysingResult;


public interface IssueProcessor {

  void run(Issue issue, IssueAnalysingResult result);

  default void setOriginalIssueToResults(Issue issue, IssueAnalysingResult result) {
    if (result.getOriginalIssue() == null) {
      result.setOriginalIssue(issue);
    }
  }
}
