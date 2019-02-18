package com.koval.jresolver.connector.jira.core.processing;

public interface IssuesGenerator<T> {
  void launch();
  T getResults();
}
