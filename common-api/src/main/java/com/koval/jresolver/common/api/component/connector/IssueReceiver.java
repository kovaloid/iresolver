package com.koval.jresolver.common.api.component.connector;

import java.util.List;

import com.koval.jresolver.common.api.bean.issue.Issue;


public interface IssueReceiver {

  boolean hasNextIssues();

  List<Issue> getNextIssues();
}
