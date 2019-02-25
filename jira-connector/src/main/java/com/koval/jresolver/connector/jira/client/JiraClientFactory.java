package com.koval.jresolver.connector.jira.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.koval.jresolver.connector.jira.client.impl.JiraClientImpl;
import com.koval.jresolver.connector.jira.configuration.auth.Credentials;
import com.koval.jresolver.connector.jira.exception.JiraConnectorException;


public final class JiraClientFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraClientFactory.class);
  private static final JiraRestClientFactory REST_CLIENT_FACTORY = new AsynchronousJiraRestClientFactory();

  private JiraClientFactory() {
  }

  public static JiraClient getAnonymousJiraClient(String host) throws JiraConnectorException {
    LOGGER.info("Creating Jira client with Anonymous authentication...");
    JiraRestClient restClient = REST_CLIENT_FACTORY.create(getURI(host), new AnonymousAuthenticationHandler());
    return new JiraClientImpl(restClient);
  }

  public static JiraClient getBasicJiraClient(String host, Credentials credentials) throws JiraConnectorException {
    LOGGER.info("Creating Jira client with Basic authentication...");
    JiraRestClient restClient = REST_CLIENT_FACTORY.createWithBasicHttpAuthentication(getURI(host),
        credentials.getUsername(), credentials.getPassword());
    return new JiraClientImpl(restClient);
  }

  private static URI getURI(String host) throws JiraConnectorException {
    try {
      return new URI(host);
    } catch (URISyntaxException e) {
      throw new JiraConnectorException("Could not initialize URI for host: " + host, e);
    }
  }
}
