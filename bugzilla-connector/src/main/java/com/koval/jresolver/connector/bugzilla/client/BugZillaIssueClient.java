package com.koval.jresolver.connector.bugzilla.client;

import java.io.IOException;
import java.util.List;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.issue.IssueField;
import com.koval.jresolver.common.api.component.connector.IssueClient;


public class BugZillaIssueClient implements IssueClient {

  @Override
  public int getTotalIssues(String query) {
    return 0;
  }

  @Override
  public List<Issue> search(String query, int maxResults, int startAt, List<String> fields) {
    return null;
  }

  @Override
  public Issue getIssueByKey(String issueKey) {
    return null;
  }

  @Override
  public List<IssueField> getIssueFields() {
    return null;
  }

  @Override
  public void close() throws IOException {

  }
}
