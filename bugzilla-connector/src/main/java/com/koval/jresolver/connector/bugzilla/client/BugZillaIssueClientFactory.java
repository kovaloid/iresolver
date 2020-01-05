package com.koval.jresolver.connector.bugzilla.client;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.auth.Credentials;
import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.connector.IssueClientFactory;
import com.koval.jresolver.connector.bugzilla.exception.BugZillaConnectorException;

import b4j.core.DefaultIssue;
import b4j.core.session.BugzillaHttpSession;
import b4j.util.HttpSessionParams;
import rs.baselib.security.AuthorizationCallback;
import rs.baselib.security.SimpleAuthorizationCallback;


public class BugZillaIssueClientFactory implements IssueClientFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(BugZillaIssueClientFactory.class);

  @Override
  public IssueClient getAnonymousClient(String host) throws BugZillaConnectorException {
    LOGGER.info("Creating BugZilla client with Anonymous authentication...");
    BugzillaHttpSession session = new BugzillaHttpSession();
    session.setBaseUrl(getURL(host));
    session.setBugzillaBugClass(DefaultIssue.class);
    return new BugZillaIssueClient(session);
  }

  @Override
  public IssueClient getBasicClient(String host, Credentials credentials) throws BugZillaConnectorException {
    LOGGER.info("Creating BugZilla client with Basic authentication...");
    BugzillaHttpSession session = new BugzillaHttpSession();
    session.setBaseUrl(getURL(host));
    session.setBugzillaBugClass(DefaultIssue.class);

    AuthorizationCallback authCallback = new SimpleAuthorizationCallback(credentials.getUsername(), credentials.getPassword());
    HttpSessionParams httpSessionParams = new HttpSessionParams();
    httpSessionParams.setBasicAuthentication(true);
    httpSessionParams.setAuthorizationCallback(authCallback);
    session.setHttpSessionParams(httpSessionParams);
    return new BugZillaIssueClient(session);
  }

  private static URL getURL(String host) throws BugZillaConnectorException {
    try {
      return new URL(host);
    } catch (MalformedURLException e) {
      throw new BugZillaConnectorException("Could not initialize URI for host: " + host, e);
    }
  }
}
