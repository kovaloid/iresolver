package com.koval.resolver.common.api.component.connector;

import java.util.List;

import com.koval.resolver.common.api.model.issue.Issue;


public interface IssueReceiver {

  boolean hasNextIssues();

  List<Issue> getNextIssues();
}
