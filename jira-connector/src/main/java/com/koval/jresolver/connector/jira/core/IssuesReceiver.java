package com.koval.jresolver.connector.jira.core;

import java.util.Collection;

import com.atlassian.jira.rest.client.api.domain.Issue;


public interface IssuesReceiver {

  boolean hasNextIssues();

  Collection<Issue> getNextIssues();
}
