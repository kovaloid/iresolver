package com.koval.jresolver.common.api.component;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.result.IssueAnalysingResult;


public interface IssueProcessor {

  void process(Issue issue, IssueAnalysingResult result);
}
