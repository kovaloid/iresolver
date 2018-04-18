package com.koval.jresolver.connector.client.impl;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.ProgressMonitor;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.bean.JiraSearchResult;
import com.koval.jresolver.connector.client.JiraClient;

import java.net.URI;
import java.net.URISyntaxException;


public class BasicJiraClient implements JiraClient {

  private static final JerseyJiraRestClientFactory FACTORY = new JerseyJiraRestClientFactory();
  private static final ProgressMonitor progressMonitor = new NullProgressMonitor();
  private JiraRestClient restClient;

  public BasicJiraClient(String host, String username, String password) throws URISyntaxException {
    restClient = FACTORY.createWithBasicHttpAuthentication(new URI(host), username, password);
  }

  public BasicJiraClient(String host) throws URISyntaxException {
    restClient = FACTORY.create(new URI(host), new AnonymousAuthenticationHandler());
  }

  @Override
  public JiraSearchResult searchByJql(String jql, int maxResults, int startAt) {
    return new JiraSearchResult(restClient.getSearchClient().searchJqlWithFullIssues(jql, maxResults, startAt, progressMonitor));
  }

  @Override
  public JiraIssue getIssueByKey(String issueKey) {
    return new JiraIssue(restClient.getIssueClient().getIssue(issueKey, progressMonitor));
  }
}
