package com.koval.jresolver.connector.jira.core.processing.handle;


public interface IssuesHandler<T, S> {

  T transform(S issue);
}
