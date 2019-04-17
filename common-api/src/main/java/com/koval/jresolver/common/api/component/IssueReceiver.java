package com.koval.jresolver.common.api.component;

import com.koval.jresolver.common.api.bean.issue.Issue;

import java.util.List;


public interface IssueReceiver {

  boolean hasNextIssues();

  List<Issue> getNextIssues();
}
