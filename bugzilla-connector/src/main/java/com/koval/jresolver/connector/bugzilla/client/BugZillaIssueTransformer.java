package com.koval.jresolver.connector.bugzilla.client;

import java.util.Collection;
import java.util.List;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.component.connector.IssueTransformer;


public class BugZillaIssueTransformer implements IssueTransformer<b4j.core.Issue> {

  @Override
  public Issue transform(b4j.core.Issue originalIssue) {
    return null;
  }

  @Override
  public List<Issue> transform(Collection<b4j.core.Issue> originalIssues) {
    return null;
  }
}
