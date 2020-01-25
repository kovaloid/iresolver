package com.koval.jresolver.connector.jira.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.koval.jresolver.common.api.auth.Credentials;
import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.connector.IssueClientFactory;
import com.koval.jresolver.connector.jira.exception.JiraConnectorException;


public class JiraIssueClientFactory implements IssueClientFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraIssueClientFactory.class);
  private static final String BROWSE_SUFFIX = "/browse/";

  @Override
  public IssueClient getAnonymousClient(String host) throws JiraConnectorException {
    LOGGER.info("Creating Jira client with Anonymous authentication...");
    JiraRestClient restClient = new AsynchronousJiraRestClientFactory().create(getURI(host),
        new AnonymousAuthenticationHandler());
    return new JiraIssueClient(restClient, host + BROWSE_SUFFIX);
  }

  @Override
  public IssueClient getBasicClient(String host, Credentials credentials) throws JiraConnectorException {
    LOGGER.info("Creating Jira client with Basic authentication...");
    JiraRestClient restClient = new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(getURI(host),
        credentials.getUsername(), credentials.getPassword());
    return new JiraIssueClient(restClient, host + BROWSE_SUFFIX);
  }

  private static URI getURI(String host) throws JiraConnectorException {
    try {
      return new URI(host);
    } catch (URISyntaxException e) {
      throw new JiraConnectorException("Could not initialize URI for host: " + host, e);
    }
  }
}
