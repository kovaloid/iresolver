package com.koval.jresolver.connector2.client.impl;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import com.atlassian.util.concurrent.Promise;
import com.koval.jresolver.connector2.bean.JiraIssue;
import com.koval.jresolver.connector2.bean.JiraSearchResult;
import com.koval.jresolver.connector2.client.JiraClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;


public class BasicJiraClient implements JiraClient {

  private static final JiraRestClientFactory FACTORY = new AsynchronousJiraRestClientFactory();
  private final JiraRestClient restClient;

  public BasicJiraClient(String host, String username, String password) throws URISyntaxException {
    restClient = FACTORY.createWithBasicHttpAuthentication(new URI(host), username, password);
  }

  public BasicJiraClient(String host) throws URISyntaxException {
    restClient = FACTORY.create(new URI(host), new AnonymousAuthenticationHandler());
  }

  @Override
  public JiraSearchResult searchByJql(String jql, int maxResults, int startAt) {
    Promise<SearchResult> searchResultPromise = restClient.getSearchClient().searchJql(jql, maxResults, startAt, new HashSet<>());
    return new JiraSearchResult(searchResultPromise.claim());
  }

  @Override
  public JiraIssue getIssueByKey(String issueKey) {
    Promise<Issue> issuePromise = restClient.getIssueClient().getIssue(issueKey);
    return new JiraIssue(issuePromise.claim());
  }
}
