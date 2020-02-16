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
import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.issue.IssueField;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.connector.IssueTransformer;
import com.koval.resolver.common.api.util.CollectionsUtil;
import com.koval.resolver.connector.jira.exception.JiraClientException;


public class JiraIssueClient implements IssueClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraIssueClient.class);

  private final JiraRestClient restClient;
  private final IssueTransformer<com.atlassian.jira.rest.client.api.domain.Issue> issueTransformer;

  JiraIssueClient(JiraRestClient restClient, String browseUrl) {
    this.restClient = restClient;
    this.issueTransformer = new JiraIssueTransformer(restClient, browseUrl);
  }

  @Override
  public int getTotalIssues(String query) {
    LOGGER.debug("Send total issues request: Query = '{}'.", query);
    SearchResult searchResult = checkRestExceptions(
        () -> restClient.getSearchClient().searchJql(query, 0, 0, getRequiredFields()).claim(),
        "Could not get total issues.");
    return searchResult.getTotal();
  }

  @Override
  public List<Issue> search(String query, int maxResults, int startAt, List<String> fields) {
    LOGGER.debug("Send search request: Query = '{}' MaxResults = '{}' StartAt = '{}'.", query, maxResults, startAt);
    if (fields.isEmpty()) {
      fields.add("*all");
    }
    if (!fields.isEmpty()) {
      fields.addAll(getRequiredFields());
    }
    final Set<String> uniqueFields = new HashSet<>(fields);
    SearchResult searchResult = checkRestExceptions(
        () -> restClient.getSearchClient().searchJql(query, maxResults, startAt, uniqueFields).claim(),
        "Could not search by JQL.");
    return issueTransformer.transform(CollectionsUtil.convert(searchResult.getIssues()));
  }

  private Set<String> getRequiredFields() {
    return new HashSet<>(Arrays.asList("summary", "issuetype", "created", "updated", "project", "status"));
  }

  @Override
  public Issue getIssueByKey(String issueKey) {
    LOGGER.debug("Send issue request: IssueKey = '{}'.", issueKey);
    com.atlassian.jira.rest.client.api.domain.Issue issue = checkRestExceptions(
        () -> restClient.getIssueClient().getIssue(issueKey).claim(),
        "Could not get issue by key: " + issueKey);
    return issueTransformer.transform(issue);
  }

  @Override
  public List<IssueField> getIssueFields() {
    Iterable<Field> fields = checkRestExceptions(
        () -> restClient.getMetadataClient().getFields().claim(),
        "Could not get fields.");
    List<IssueField> issueFields = new ArrayList<>();
    fields.forEach(field -> {
      IssueField issueField = new IssueField();
      issueField.setId(field.getId());
      issueField.setName(field.getName());
      issueField.setType(field.getFieldType().name());
      issueField.setValue("");
      issueFields.add(issueField);
    });
    return issueFields;
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
