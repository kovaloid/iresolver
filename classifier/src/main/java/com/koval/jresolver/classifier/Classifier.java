package com.koval.jresolver.classifier;

import java.net.URISyntaxException;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.classifier.impl.ClassifierResult;


public interface Classifier {
  void prepare();
  void configure();
  ClassifierResult execute(Issue actualIssue) throws URISyntaxException;
}
