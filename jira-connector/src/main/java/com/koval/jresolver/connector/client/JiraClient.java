package com.koval.jresolver.connector.client;

import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.bean.JiraSearchResult;


public interface JiraClient {

  JiraSearchResult searchByJql(String jql, int maxResults, int startAt);

  JiraIssue getIssueByKey(String issueKey);
}
