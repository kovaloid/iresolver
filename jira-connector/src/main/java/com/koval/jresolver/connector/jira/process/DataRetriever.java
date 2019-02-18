package com.koval.jresolver.connector.jira.process;


public interface DataRetriever {

  void start();

  void stop();

  double getStatus();
}
