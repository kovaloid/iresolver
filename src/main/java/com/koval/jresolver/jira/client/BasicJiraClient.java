package com.koval.jresolver.jira.client;

import java.net.URI;
import java.net.URISyntaxException;

import com.atlassian.jira.rest.client.IssueRestClient;
import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.ProgressMonitor;
import com.atlassian.jira.rest.client.SearchRestClient;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;


public class BasicJiraClient implements JiraClient {

  private static final JerseyJiraRestClientFactory FACTORY = new JerseyJiraRestClientFactory();
  private ProgressMonitor progressMonitor = new NullProgressMonitor();
  private JiraRestClient restClient;

  public BasicJiraClient(String host, String username, String password) throws URISyntaxException {
    restClient = FACTORY.createWithBasicHttpAuthentication(new URI(host), username, password);
  }

  public JiraRestClient getRestClient() {
    return restClient;
  }

  public ProgressMonitor getMonitor() {
    return progressMonitor;
  }

  public IssueRestClient getIssueClient() {
    return restClient.getIssueClient();
  }

  public SearchRestClient getSearchClient() {
    return restClient.getSearchClient();
  }
}
