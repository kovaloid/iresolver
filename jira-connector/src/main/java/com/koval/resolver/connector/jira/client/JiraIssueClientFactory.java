package com.koval.resolver.connector.jira.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.koval.resolver.common.api.auth.Credentials;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.connector.IssueClientFactory;
import com.koval.resolver.common.api.configuration.bean.connectors.JiraConnectorConfiguration;
import com.koval.resolver.connector.jira.exception.JiraConnectorException;


public class JiraIssueClientFactory implements IssueClientFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraIssueClientFactory.class);
  private static final String BROWSE_SUFFIX = "/browse/";

  private final String host;
  private final Credentials credentials;

  public JiraIssueClientFactory(JiraConnectorConfiguration connectorConfiguration) {
    host = connectorConfiguration.getUrl();
    if (connectorConfiguration.isAnonymous()) {
      credentials = null;
    } else {
      credentials = Credentials.getCredentials(connectorConfiguration.getCredentialsFolder());
    }
  }

  @Override
  public IssueClient getClient() throws JiraConnectorException {
    if (credentials == null) {
      return getAnonymousClient();
    } else {
      return getBasicClient();
    }
  }

  private IssueClient getAnonymousClient() throws JiraConnectorException {
    LOGGER.info("Creating Jira client with Anonymous authentication...");
    JiraRestClient restClient = new AsynchronousJiraRestClientFactory().create(getURI(host),
            new AnonymousAuthenticationHandler());
    return new JiraIssueClient(restClient, host + BROWSE_SUFFIX);
  }

  private IssueClient getBasicClient() throws JiraConnectorException {
    if (credentials == null) {
      throw new IllegalStateException("Credentials were not provided, impossible to create basic Jira client");
    }

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
