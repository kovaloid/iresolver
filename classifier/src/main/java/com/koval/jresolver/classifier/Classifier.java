package com.koval.jresolver.classifier;

import java.io.IOException;
import java.net.URISyntaxException;

import com.koval.jresolver.classifier.impl.ClassifierResult;
import com.koval.jresolver.connector.bean.JiraIssue;


public interface Classifier {
  void prepare();
  void configure() throws IOException;
  ClassifierResult execute(JiraIssue actualIssue) throws URISyntaxException;
  Vectorizer getVectorizer();
}
