package com.koval.resolver.connector.gitlab.client;

import com.koval.resolver.common.api.auth.Credentials;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.connector.IssueClientFactory;
import com.koval.resolver.common.api.configuration.bean.connectors.GitlabConnectorConfiguration;
import com.koval.resolver.connector.gitlab.exception.GitlabConnectorException;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitlabIssueClientFactory implements IssueClientFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(GitlabIssueClientFactory.class);

  private final String host;
  private final Credentials credentials;

  public GitlabIssueClientFactory(final GitlabConnectorConfiguration connectorConfiguration) {
    host = connectorConfiguration.getUrl();
    credentials = Credentials.getCredentials(connectorConfiguration.getCredentialsFolder());
  }

  @Override
  public IssueClient getClient() throws GitlabConnectorException {
    return getBasicClient();
  }

  private IssueClient getBasicClient() throws GitlabConnectorException {
    if (credentials == null) {
      throw new IllegalStateException("Credentials were not provided, impossible to create basic GitLab client");
    }
    LOGGER.info("Creating GitLab client with Basic authentication...");

    final GitLabApi gitLabApi;
    try {
      gitLabApi = GitLabApi.oauth2Login(host, credentials.getUsername(), credentials.getPassword());
    } catch (GitLabApiException e) {
      throw new GitlabConnectorException(e.getMessage(), e);
    }
    return new GitlabIssueClient(gitLabApi);
  }
}
