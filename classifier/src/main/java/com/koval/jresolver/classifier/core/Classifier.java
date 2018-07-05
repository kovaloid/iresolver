package com.koval.jresolver.classifier.core;

import java.io.IOException;
import java.net.URISyntaxException;

import com.koval.jresolver.classifier.core.impl.DocClassifier;
import com.koval.jresolver.classifier.results.ClassifierResult;
import com.koval.jresolver.connector.bean.JiraIssue;


public interface Classifier {

  static boolean checkPrepare() {
    return DocClassifier.checkPrepare();
  }

  static boolean checkConfigure() {
    return DocClassifier.checkConfigure();
  }

  void prepare() throws IOException;

  void configure() throws IOException;

  ClassifierResult execute(JiraIssue actualIssue) throws URISyntaxException;

  Vectorizer getVectorizer();
}
