package com.koval.jresolver.common.api.component.processor;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;


public interface IssueProcessor {

  void run(Issue issue, IssueAnalysingResult result);

  default void setOriginalIssueToResults(Issue issue, IssueAnalysingResult result) {
    if (result.getOriginalIssue() == null) {
      result.setOriginalIssue(issue);
    }
  }
}
