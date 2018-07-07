package com.koval.jresolver.classifier.core;

import java.io.IOException;
import java.net.URISyntaxException;

import com.koval.jresolver.classifier.results.ClassifierResult;
import com.koval.jresolver.connector.bean.JiraIssue;


public interface Classifier {

  void prepare() throws IOException;

  void configure() throws IOException;

  ClassifierResult execute(JiraIssue actualIssue, String vectorModelResource) throws URISyntaxException;

  Vectorizer getVectorizer();
}
