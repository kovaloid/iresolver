package com.koval.jresolver.jira.client;

import com.atlassian.jira.rest.client.IssueRestClient;
import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.ProgressMonitor;
import com.atlassian.jira.rest.client.SearchRestClient;


public interface JiraClient {
  JiraRestClient getRestClient();

  ProgressMonitor getMonitor();

  IssueRestClient getIssueClient();

  SearchRestClient getSearchClient();
}
