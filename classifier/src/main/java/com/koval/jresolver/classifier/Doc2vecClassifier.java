package com.koval.jresolver.classifier;


import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.classifier.doc2vec.DocVectorizer;
import com.koval.jresolver.connector.client.JiraClient;
import com.koval.jresolver.connector.client.impl.BasicJiraClient;

import java.util.Collection;

public class Doc2vecClassifier {

  private DocVectorizer docVectorizer = new DocVectorizer();

  public void train(String datasetFileName) {

    docVectorizer.createFromResourceWithoutLabels("/raw_sentences.txt");
    //docVectorizer.load("docVectors.dv");
  }

  public ClassifierResult execute(Issue actualIssue) {
    Collection<String> keys = docVectorizer.getNearestLabels(actualIssue.getDescription(), 10);
    JiraClient jiraClient = new BasicJiraClient("http://sdfsd");

    ClassifierResult result;

    keys.forEach((key) -> {
      jiraClient.getIssueByKey(key);
    });


    return result;
  }

}
