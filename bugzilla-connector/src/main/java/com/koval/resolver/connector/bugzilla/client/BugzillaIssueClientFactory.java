package com.koval.resolver.connector.bugzilla.client;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.auth.Credentials;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.connector.IssueClientFactory;
import com.koval.resolver.common.api.configuration.bean.connectors.BugzillaConnectorConfiguration;
import com.koval.resolver.connector.bugzilla.exception.BugzillaConnectorException;

import b4j.core.DefaultIssue;
import b4j.core.session.BugzillaHttpSession;
import b4j.util.HttpSessionParams;
import rs.baselib.security.AuthorizationCallback;
import rs.baselib.security.SimpleAuthorizationCallback;


public class BugzillaIssueClientFactory implements IssueClientFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(BugzillaIssueClientFactory.class);

  private final String host;
  private final Credentials credentials;

  public BugzillaIssueClientFactory(BugzillaConnectorConfiguration connectorConfiguration) {
    host = connectorConfiguration.getUrl();
    if (connectorConfiguration.isAnonymous()) {
      credentials = null;
    } else {
      credentials = Credentials.getCredentials(connectorConfiguration.getCredentialsFolder());
    }
  }

  @Override
  public IssueClient getClient() throws BugzillaConnectorException {
    if (credentials == null) {
      return getAnonymousClient();
    } else {
      return getBasicClient();
    }
  }

  private IssueClient getAnonymousClient() throws BugzillaConnectorException {
    LOGGER.info("Creating BugZilla client with Anonymous authentication...");
    BugzillaHttpSession session = new BugzillaHttpSession();
    session.setBaseUrl(getURL(host));
    session.setBugzillaBugClass(DefaultIssue.class);
    return new BugzillaIssueClient(session);
  }

  private IssueClient getBasicClient() throws BugzillaConnectorException {
    if (credentials == null) {
      throw new IllegalStateException("Credentials were not provided, impossible to create basic Bugzilla client");
    }

    LOGGER.info("Creating BugZilla client with Basic authentication...");
    BugzillaHttpSession session = new BugzillaHttpSession();
    session.setBaseUrl(getURL(host));
    session.setBugzillaBugClass(DefaultIssue.class);

    AuthorizationCallback authCallback = new SimpleAuthorizationCallback(credentials.getUsername(), credentials.getPassword());
    HttpSessionParams httpSessionParams = new HttpSessionParams();
    httpSessionParams.setBasicAuthentication(true);
    httpSessionParams.setAuthorizationCallback(authCallback);
    session.setHttpSessionParams(httpSessionParams);
    return new BugzillaIssueClient(session);
  }

  private static URL getURL(String host) throws BugzillaConnectorException {
    try {
      return new URL(host);
    } catch (MalformedURLException e) {
      throw new BugzillaConnectorException("Could not initialize URI for host: " + host, e);
    }
  }
}
