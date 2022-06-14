package com.koval.resolver.connector.jira.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Field;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.connector.IssueTransformer;
import com.koval.resolver.common.api.model.issue.Issue;
import com.koval.resolver.common.api.model.issue.IssueField;
import com.koval.resolver.common.api.util.CollectionsUtil;
import com.koval.resolver.connector.jira.exception.JiraClientException;


public class JiraIssueClient implements IssueClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraIssueClient.class);

  private final JiraRestClient restClient;
  private final IssueTransformer<com.atlassian.jira.rest.client.api.domain.Issue> issueTransformer;

  JiraIssueClient(final JiraRestClient restClient, final String browseUrl) {
    this.restClient = restClient;
    this.issueTransformer = new JiraIssueTransformer(restClient, browseUrl);
  }

  @Override
  public int getTotalIssues(final String query) {
    LOGGER.debug("Send total issues request: Query = '{}'.", query);
    final SearchResult searchResult = checkRestExceptions(
        () -> restClient.getSearchClient().searchJql(query, 0, 0, getRequiredFields()).claim(),
        "Could not get total issues.");
    return searchResult.getTotal();
  }

  @Override
  public List<Issue> search(final String query, final int maxResults, final int startAt, final List<String> fields) {
    LOGGER.debug("Send search request: Query = '{}' MaxResults = '{}' StartAt = '{}'.", query, maxResults, startAt);
    if (fields.isEmpty()) {
      fields.add("*all");
    }
    if (!fields.isEmpty()) {
      fields.addAll(getRequiredFields());
    }
    final Set<String> uniqueFields = new HashSet<>(fields);
    final SearchResult searchResult = checkRestExceptions(
        () -> restClient.getSearchClient().searchJql(query, maxResults, startAt, uniqueFields).claim(),
        "Could not search by JQL.");
    return issueTransformer.transform(CollectionsUtil.convert(searchResult.getIssues()));
  }

  private Set<String> getRequiredFields() {
    return new HashSet<>(Arrays.asList("summary", "issuetype", "created", "updated", "project", "status"));
  }

  @Override
  public Issue getIssueByKey(final String issueKey) {
    LOGGER.debug("Send issue request: IssueKey = '{}'.", issueKey);
    final com.atlassian.jira.rest.client.api.domain.Issue issue = checkRestExceptions(
        () -> restClient.getIssueClient().getIssue(issueKey).claim(),
        "Could not get issue by key: " + issueKey);
    return issueTransformer.transform(issue);
  }

  @Override
  public List<IssueField> getIssueFields() {
    final Iterable<Field> fields = checkRestExceptions(
        () -> restClient.getMetadataClient().getFields().claim(),
        "Could not get fields.");
    if (fields == null) {
      LOGGER.warn("Fields does not exist");
      return new ArrayList<>();
    }
    final List<IssueField> issueFields = new ArrayList<>();
    fields.forEach(field -> {
      final IssueField issueField = new IssueField();
      issueField.setId(field.getId());
      issueField.setName(field.getName());
      issueField.setType(field.getFieldType().name());
      issueField.setValue("");
      issueFields.add(issueField);
    });
    return issueFields;
  }

  @SuppressWarnings("PMD.PreserveStackTrace")
  private <T> T checkRestExceptions(final Supplier<T> supplier, final String message) {
    try {
      return supplier.get();
    } catch (RestClientException e) {
      e.getErrorCollections().forEach(errorCollection -> errorCollection.getErrorMessages().forEach(LOGGER::error));
      if (e.getStatusCode().isPresent()) {
        handleStatusCode(e.getStatusCode().get());
      }
      throw new JiraClientException(message, e);
    }
  }

  private void handleStatusCode(int statusCode) {
    switch (statusCode) {
      case 400:
        throw new JiraClientException("Bad Request. Incorrect JQL.");
      case 401:
        throw new JiraClientException("Unauthorized. Incorrect login or password.");
      case 404:
        LOGGER.warn("Could not find issue with such key in Jira. Please, check keys in the data set.");
        break;
      default:
        break;
    }
  }

  @Override
  public void close() throws IOException {
    LOGGER.info("Closing Jira client...");
    restClient.close();
  }
}
