package com.koval.resolver.connector.bugzilla.client;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.auth.Credentials;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.connector.IssueClientFactory;
import com.koval.resolver.connector.bugzilla.exception.BugzillaConnectorException;

import b4j.core.DefaultIssue;
import b4j.core.session.BugzillaHttpSession;
import b4j.util.HttpSessionParams;
import rs.baselib.security.AuthorizationCallback;
import rs.baselib.security.SimpleAuthorizationCallback;


public class BugzillaIssueClientFactory implements IssueClientFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(BugzillaIssueClientFactory.class);

  @Override
  public IssueClient getAnonymousClient(String host) throws BugzillaConnectorException {
    LOGGER.info("Creating BugZilla client with Anonymous authentication...");
    BugzillaHttpSession session = new BugzillaHttpSession();
    session.setBaseUrl(getURL(host));
    session.setBugzillaBugClass(DefaultIssue.class);
    return new BugzillaIssueClient(session);
  }

  @Override
  public IssueClient getBasicClient(String host, Credentials credentials) throws BugzillaConnectorException {
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
