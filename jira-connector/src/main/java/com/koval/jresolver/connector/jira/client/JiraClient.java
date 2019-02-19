package com.koval.jresolver.connector.jira.client;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;


public interface JiraClient {

  SearchResult searchByJql(String jql, int maxResults, int startAt);

  Issue getIssueByKey(String issueKey);
}
