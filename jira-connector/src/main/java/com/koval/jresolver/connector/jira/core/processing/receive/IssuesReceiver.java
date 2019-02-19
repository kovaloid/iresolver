package com.koval.jresolver.connector.jira.core.processing.receive;

import java.util.Collection;

import com.atlassian.jira.rest.client.api.domain.Issue;


public interface IssuesReceiver {

  boolean hasNextIssues();

  Collection<Issue> getNextIssues();
}
