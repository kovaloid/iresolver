package com.koval.jresolver.connector.jira;

import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.core.IssuesReceiver;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class WhenCreatingJiraConnector {

  private JiraConnector jiraConnector;

  @Before
  public void init() {
    JiraClient client = mock(JiraClient.class);
    ConnectorProperties properties = mock(ConnectorProperties.class);
    SearchResult searchResult = mock(SearchResult.class);
    when(properties.getResolvedQuery()).thenReturn("resolvedQuery");
    when(properties.getUnresolvedQuery()).thenReturn("unresolvedQuery");
    when(searchResult.getTotal()).thenReturn(0);
    when(client.searchByJql(anyString(), anyInt(), anyInt())).thenReturn(searchResult);
    jiraConnector = new JiraConnector(client, properties);
  }

  @Test
  public void shouldBeAbleToGetResolvedIssuesReceiver() {
    IssuesReceiver receiver = jiraConnector.getResolvedIssuesReceiver();
    assertFalse("Check next issues", receiver.hasNextIssues());
  }

  @Test
  public void shouldBeAbleToGetUnresolvedIssuesReceiver() {
    IssuesReceiver receiver = jiraConnector.getUnresolvedIssuesReceiver();
    assertFalse("Check next issues", receiver.hasNextIssues());
  }
}
