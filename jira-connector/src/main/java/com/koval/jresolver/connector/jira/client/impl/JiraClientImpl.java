package com.koval.jresolver.connector.jira.client.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.domain.Field;
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
    return searchByJql(jql, maxResults, startAt, new HashSet<>());
  }

  @Override
  public SearchResult searchByJql(String jql, int maxResults, int startAt, final Set<String> fields) {
    LOGGER.debug("Send search request: JQL = '{}' MaxResults = '{}' StartAt = '{}'.", jql, maxResults, startAt);
    if (!fields.isEmpty()) {
      fields.addAll(getRequiredFields());
    }
    if (fields.isEmpty()) {
      fields.add("*all");
    }
    return checkRestExceptions(() -> restClient.getSearchClient().searchJql(jql, maxResults, startAt, fields).claim(),
        "Could not search by JQL.");
  }

  private Set<String> getRequiredFields() {
    return new HashSet<>(Arrays.asList("summary", "issuetype", "created", "updated", "project", "status"));
  }

  @Override
  public Issue getIssueByKey(String issueKey) {
    LOGGER.debug("Send issue request: IssueKey = '{}'.", issueKey);
    return checkRestExceptions(() -> restClient.getIssueClient().getIssue(issueKey).claim(),
        "Could not get issue by key.");
  }

  @Override
  public Iterable<Field> getFields() {
    return checkRestExceptions(() -> restClient.getMetadataClient().getFields().claim(),
            "Could not get fields.");
  }

  @SuppressWarnings("PMD.PreserveStackTrace")
  private <T> T checkRestExceptions(Supplier<T> supplier, String message) {
    try {
      return supplier.get();
    } catch (RestClientException e) {
      e.getErrorCollections().forEach(errorCollection -> errorCollection.getErrorMessages().forEach(LOGGER::error));
      if (e.getStatusCode().isPresent() && e.getStatusCode().get().equals(400)) {
        throw new JiraClientException("Bad Request. Incorrect JQL.");
      }
      if (e.getStatusCode().isPresent() && e.getStatusCode().get().equals(401)) {
        throw new JiraClientException("Unauthorized. Incorrect login or password.");
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
