package com.koval.jresolver.connector2.client;

import com.koval.jresolver.connector2.bean.JiraIssue;
import com.koval.jresolver.connector2.bean.JiraSearchResult;

public interface JiraClient {

  JiraSearchResult searchByJql(String jql, int maxResults, int startAt);

  JiraIssue getIssueByKey(String issueKey);
}
