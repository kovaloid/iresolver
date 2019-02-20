package com.koval.jresolver.connector.jira.core.processing.generate;


public interface IssuesGenerator<T> {

  void launch();

  T getResults();
}
