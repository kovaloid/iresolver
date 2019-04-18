package com.koval.jresolver.connector.jira;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;
import com.koval.jresolver.connector.jira.configuration.JiraConnectorProperties;
import com.koval.jresolver.connector.jira.core.JiraIssueReceiver;


public class WhenReceivingJiraIssues {

  private IssueReceiver receiver;

  @Before
  public void init() {
    IssueClient client = mock(IssueClient.class);
    JiraConnectorProperties connectorProperties = mock(JiraConnectorProperties.class);
    when(connectorProperties.getResolvedQuery()).thenReturn("resolvedQuery");
    when(client.getTotalIssues(anyString())).thenReturn(10);
    receiver = new JiraIssueReceiver(client, connectorProperties, true);
  }

  @Test
  public void shouldGetResolvedIssues() {
    assertTrue(receiver.hasNextIssues());
  }
}
