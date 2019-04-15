package com.koval.jresolver.connector.jira;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.connector.jira.core.IssuesReceiver;
import com.koval.jresolver.connector.jira.core.impl.IssuesReceiverImpl;


public class WhenReceivingJiraIssues {

  private IssuesReceiver receiver;

  @Before
  public void init() {
    JiraClient client = mock(JiraClient.class);
    ConnectorProperties connectorProperties = mock(ConnectorProperties.class);
    SearchResult result = mock(SearchResult.class);
    when(connectorProperties.getResolvedQuery()).thenReturn("resolvedQuery");
    when(result.getTotal()).thenReturn(10);
    when(client.searchByJql(anyString(), anyInt(), anyInt())).thenReturn(result);
    receiver = new IssuesReceiverImpl(client, connectorProperties, true);
  }

  @Test
  public void shouldGetResolvedIssues() {
    assertTrue(receiver.hasNextIssues());
  }
}
