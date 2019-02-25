package com.koval.jresolver.connector.jira.client.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.exception.JiraClientException;


public class JiraClientImpl implements JiraClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraClientImpl.class);

  private final JiraRestClient restClient;

  public JiraClientImpl(JiraRestClient restClient) {
    this.restClient =  restClient;
  }

  @Override
  public SearchResult searchByJql(String jql, int maxResults, int startAt) {
    LOGGER.debug("Send search request: JQL = '{}' MaxResults = '{}' StartAt = '{}'.", jql, maxResults, startAt);
    return checkAuthentication(() -> restClient.getSearchClient().searchJql(jql, maxResults, startAt, new HashSet<>()).claim(),
        "Could not search by JQL.");
  }

  @Override
  public Issue getIssueByKey(String issueKey) {
    LOGGER.debug("Send issue request: IssueKey = '{}'.", issueKey);
    return checkAuthentication(() -> restClient.getIssueClient().getIssue(issueKey).claim(),
        "Could not get issue by key.");
  }

  @SuppressWarnings("PMD.PreserveStackTrace")
  private <T> T checkAuthentication(Supplier<T> supplier, String message) {
    try {
      return supplier.get();
    } catch (RestClientException e) {
      e.getErrorCollections().forEach(errorCollection -> errorCollection.getErrorMessages().forEach(LOGGER::error));
      if (e.getStatusCode().isPresent() && e.getStatusCode().get().equals(400)) {
        throw new JiraClientException("Bad Request.");
      }
      if (e.getStatusCode().isPresent() && e.getStatusCode().get().equals(401)) {
        throw new JiraClientException("Unauthorized.");
      }
      throw new JiraClientException(message, e);
    }
  }

  @Override
  public void close() throws IOException {
    LOGGER.info("Closing Jira client...");
    restClient.close();
  }
}
