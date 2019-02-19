package com.koval.jresolver.classifier.core;

import java.io.IOException;
import java.net.URISyntaxException;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.classifier.results.ClassifierResult;


public interface Classifier {

  void prepare() throws IOException;

  void configure() throws IOException;

  ClassifierResult execute(Issue actualIssue) throws URISyntaxException;

  Vectorizer getVectorizer();
}
