package com.koval.jresolver.connector.client;

import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.SearchResult;


public interface JiraClient {
  SearchResult searchByJql(String jql, int maxResults, int startAt);
  Issue getIssueByKey(String issueKey);
}
