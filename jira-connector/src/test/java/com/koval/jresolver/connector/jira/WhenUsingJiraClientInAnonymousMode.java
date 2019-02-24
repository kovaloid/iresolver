package com.koval.jresolver.connector.jira;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.jira.exception.JiraConnectorException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class WhenUsingJiraClientInAnonymousMode {

  private JiraClient jiraClient;
  private JiraRestClient jiraRestClient;

  @Before
  public void init() throws JiraConnectorException {
    jiraClient = new BasicJiraClient("http://test.com");
    jiraRestClient = mock(JiraRestClient.class);
    ((BasicJiraClient) jiraClient).setCustomRestClient(jiraRestClient);
  }

  @Test
  public void shouldSearchIssuesByQuery() {
    SearchResult expectedSearchResult = mock(SearchResult.class);
    Promise<SearchResult> promise = (Promise<SearchResult>) mock(Promise.class);
    SearchRestClient searchRestClient = mock(SearchRestClient.class);
    when(promise.claim()).thenReturn(expectedSearchResult);
    when(searchRestClient.searchJql(anyString(), anyInt(), anyInt(), anySet())).thenReturn(promise);
    when(promise.claim()).thenReturn(expectedSearchResult);
    when(jiraRestClient.getSearchClient()).thenReturn(searchRestClient);
    when(expectedSearchResult.getTotal()).thenReturn(0);

    SearchResult actualSearchResult = jiraClient.searchByJql("testJql", 0, 0);
    assertEquals("Search result", expectedSearchResult, actualSearchResult);
  }

  @Test
  public void shouldGetIssueByKey() {
    Issue expectedIssue = mock(Issue.class);
    Promise<Issue> promise = (Promise<Issue>) mock(Promise.class);
    IssueRestClient issueRestClient = mock(IssueRestClient.class);
    when(promise.claim()).thenReturn(expectedIssue);
    when(issueRestClient.getIssue(anyString())).thenReturn(promise);
    when(promise.claim()).thenReturn(expectedIssue);
    when(jiraRestClient.getIssueClient()).thenReturn(issueRestClient);

    Issue actualIssue = jiraClient.getIssueByKey("testKey");
    assertEquals("Issue", expectedIssue, actualIssue);
  }
}
