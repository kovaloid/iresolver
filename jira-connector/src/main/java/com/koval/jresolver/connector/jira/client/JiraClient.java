package com.koval.jresolver.connector.jira.client;

import java.io.Closeable;
import java.util.Set;

import com.atlassian.jira.rest.client.api.domain.Field;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;


public interface JiraClient extends Closeable {

  SearchResult searchByJql(String jql, int maxResults, int startAt);

  SearchResult searchByJql(String jql, int maxResults, int startAt, Set<String> fields);

  Issue getIssueByKey(String issueKey);

  Iterable<Field> getFields();
}
