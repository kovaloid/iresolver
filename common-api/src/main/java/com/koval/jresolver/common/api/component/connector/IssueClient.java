package com.koval.jresolver.common.api.component.connector;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.issue.IssueField;


public interface IssueClient extends Closeable {

  int getTotalIssues(String query);

  List<Issue> search(String query, int maxResults, int startAt, List<String> fields);

  Issue getIssueByKey(String issueKey);

  List<IssueField> getIssueFields();

  void close() throws IOException;
}
