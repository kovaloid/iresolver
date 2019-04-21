package com.koval.jresolver.connector.jira;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;
import com.koval.jresolver.connector.jira.configuration.JiraConnectorProperties;


public class WhenCreatingJiraConnector {

  private JiraConnector jiraConnector;

  @Before
  public void init() {
    IssueClient client = mock(IssueClient.class);
    JiraConnectorProperties properties = mock(JiraConnectorProperties.class);
    List searchResult = mock(List.class);
    when(properties.getResolvedQuery()).thenReturn("resolvedQuery");
    when(properties.getUnresolvedQuery()).thenReturn("unresolvedQuery");
    when(client.search(anyString(), anyInt(), anyInt(), anyList())).thenReturn(searchResult);
    jiraConnector = new JiraConnector(client, properties);
  }

  @Test
  public void shouldBeAbleToGetResolvedIssuesReceiver() {
    IssueReceiver receiver = jiraConnector.getResolvedIssuesReceiver();
    assertFalse("Check next issues", receiver.hasNextIssues());
  }

  @Test
  public void shouldBeAbleToGetUnresolvedIssuesReceiver() {
    IssueReceiver receiver = jiraConnector.getUnresolvedIssuesReceiver();
    assertFalse("Check next issues", receiver.hasNextIssues());
  }
}
