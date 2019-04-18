package com.koval.jresolver.connector.bugzilla.core;

import java.util.List;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;


public class BugZillaIssueReceiver implements IssueReceiver {

  @Override
  public boolean hasNextIssues() {
    return false;
  }

  @Override
  public List<Issue> getNextIssues() {
    return null;
  }
}
