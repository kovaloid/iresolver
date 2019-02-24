package com.koval.jresolver.connector.jira.client.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.auth.Credentials;
import com.koval.jresolver.connector.jira.exception.JiraConnectorException;


public class BasicJiraClient implements JiraClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(BasicJiraClient.class);

  private final JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
  private JiraRestClient restClient;

  public BasicJiraClient(String host, Credentials credentials) throws JiraConnectorException {
    restClient = factory.createWithBasicHttpAuthentication(getURI(host), credentials.getUsername(), credentials.getPassword());
    LOGGER.info("Jira client with Basic authentication was created.");
  }

  public BasicJiraClient(String host) throws JiraConnectorException {
    restClient = factory.create(getURI(host), new AnonymousAuthenticationHandler());
    LOGGER.info("Jira client with Anonymous authentication was created.");
  }

  private URI getURI(String host) throws JiraConnectorException {
    try {
      return new URI(host);
    } catch (URISyntaxException e) {
      throw new JiraConnectorException("Could not initialize URI for host: " + host, e);
    }
  }

  public void setCustomRestClient(JiraRestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public SearchResult searchByJql(String jql, int maxResults, int startAt) {
    LOGGER.debug("Send search request: JQL = '{}' MaxResults = '{}' StartAt = '{}'.", jql, maxResults, startAt);
    return restClient.getSearchClient().searchJql(jql, maxResults, startAt, new HashSet<>()).claim();
  }

  @Override
  public Issue getIssueByKey(String issueKey) {
    LOGGER.debug("Send issue request: IssueKey = '{}'.", issueKey);
    return restClient.getIssueClient().getIssue(issueKey).claim();
  }

  @Override
  public void close() throws IOException {
    LOGGER.info("Closing Jira client...");
    restClient.close();
  }
}
