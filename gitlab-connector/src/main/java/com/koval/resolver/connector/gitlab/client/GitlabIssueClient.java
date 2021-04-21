package com.koval.resolver.connector.gitlab.client;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.IssueFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.issue.IssueField;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.connector.IssueTransformer;
import com.koval.resolver.connector.gitlab.configuration.GitlabQuery;
import com.koval.resolver.connector.gitlab.configuration.GitlabQueryParser;
import com.koval.resolver.connector.gitlab.exception.GitlabClientException;

public class GitlabIssueClient implements IssueClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(GitlabIssueClient.class);

  private final GitLabApi apiClient;
  private final IssueTransformer<org.gitlab4j.api.models.Issue> issueTransformer;

  public GitlabIssueClient(GitLabApi apiClient) {
    this.apiClient = apiClient;
    this.issueTransformer = new GitlabIssueTransformer(apiClient);
  }

  @Override
  public int getTotalIssues(String query) {
    LOGGER.debug("Send total issues request: Query = '{}'.", query);
    final GitlabQueryParser queryParser = new GitlabQueryParser();
    final GitlabQuery parsedQuery = queryParser.parse(query);
    final IssueFilter issueFilter = getIssueFilterByQuery(parsedQuery);
    try {
      return apiClient.getIssuesApi().getIssues(parsedQuery.getProject(), issueFilter).size();
    } catch (GitLabApiException e) {
      throw new GitlabClientException("Could not get total issues.", e);
    }
  }

  @Override
  public List<Issue> search(String query, int maxResults, int startAt, List<String> fields) {
    LOGGER.debug("Send search request: Query = '{}' MaxResults = '{}' StartAt = '{}'.", query, maxResults, startAt);
    if (startAt % maxResults != 0) {
      throw new GitlabClientException("Could not calculate page size. 'startAt' must be divisible by 'maxResults'");
    }
    final GitlabQueryParser queryParser = new GitlabQueryParser();
    final GitlabQuery parsedQuery = queryParser.parse(query);
    final IssueFilter issueFilter = getIssueFilterByQuery(parsedQuery);
    List<org.gitlab4j.api.models.Issue> issues = null;
    try {
      issues = apiClient.getIssuesApi()
              .getIssues(parsedQuery.getProject(), issueFilter, startAt / maxResults + 1, maxResults);
    } catch (GitLabApiException e) {
      throw new GitlabClientException("Could not search by query.", e);
    }
    return issueTransformer.transform(issues);
  }

  @Override
  public Issue getIssueByKey(String issueKey) {
    final IssueFilter filter = new IssueFilter();
    filter.setIids(Collections.singletonList(issueKey));
    org.gitlab4j.api.models.Issue issue = null;
    try {
      issue = apiClient.getIssuesApi().getIssues(filter, 1, 1).get(0);
    } catch (GitLabApiException e) {
      throw new GitlabClientException("Could not get by id.", e);
    }
    return issueTransformer.transform(issue);
  }

  @Override
  public List<IssueField> getIssueFields() {
    return Collections.emptyList();
  }

  @Override
  public void close() {
    apiClient.close();
  }

  private IssueFilter getIssueFilterByQuery(GitlabQuery query) {
    final IssueFilter issueFilter = new IssueFilter();
    if (query.getStatus() != null) {
      issueFilter.setState(Constants.IssueState.valueOf(query.getStatus().toUpperCase(Locale.getDefault())));
    }
    if (query.getLabel() != null) {
      issueFilter.setLabels(Collections.singletonList(query.getLabel()));
    }
    if (query.getAssigneeId() != null) {
      issueFilter.setAssigneeId(query.getAssigneeId());
    }
    if (query.getMilestone() != null) {
      issueFilter.setMilestone(query.getMilestone());
    }
    if (query.getAuthorId() != null) {
      issueFilter.setAuthorId(query.getAuthorId());
    }
    return issueFilter;
  }
}
