package com.koval.jresolver.connector.jira.client;

import com.koval.jresolver.connector.jira.bean.JiraIssue;
import com.koval.jresolver.connector.jira.bean.JiraSearchResult;

public interface JiraClient {

  JiraSearchResult searchByJql(String jql, int maxResults, int startAt);

  JiraIssue getIssueByKey(String issueKey);
}
