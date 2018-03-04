package com.koval.jresolver.extraction.client.impl;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.ProgressMonitor;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;
import com.koval.jresolver.extraction.client.JiraClient;

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
  public SearchResult searchByJql(String jql, int maxResults, int startAt) {
    return restClient.getSearchClient().searchJqlWithFullIssues(jql, maxResults, startAt, progressMonitor);
  }

  @Override
  public Issue getIssueByKey(String issueKey) {
    return restClient.getIssueClient().getIssue(issueKey, progressMonitor);
  }
}
