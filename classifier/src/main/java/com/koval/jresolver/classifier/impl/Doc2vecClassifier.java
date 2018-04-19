package com.koval.jresolver.classifier.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.classifier.Classifier;
import com.koval.jresolver.classifier.Vectorizer;
import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.client.JiraClient;
import com.koval.jresolver.connector.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.configuration.JiraProperties;


public class Doc2vecClassifier implements Classifier {

  private static final Logger LOGGER = LoggerFactory.getLogger(Doc2vecClassifier.class);
  private static final String DATASET_FILE_NAME = "dataset.txt";
  private static final String VECTOR_MODEL_FILE_NAME = "vectors.zip";
  private static final int NUMBER_OF_NEAREST_LABELS = 10;

  private final DocVectorizer docVectorizer;
  private final JiraConnector jiraConnector;
  private final JiraClient jiraClient;

  public Doc2vecClassifier(ClassifierProperties classifierProperties) throws URISyntaxException, IOException {
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    jiraConnector = new JiraConnector(jiraProperties);
    jiraClient = new BasicJiraClient(jiraProperties.getUrl());
    docVectorizer = new DocVectorizer(classifierProperties);
  }

  @Override
  public void prepare() {
    jiraConnector.createHistoryIssuesDataSet(DATASET_FILE_NAME);
  }

  @Override
  public void configure() throws IOException {
    docVectorizer.createFromDataset(DATASET_FILE_NAME);
    docVectorizer.save(VECTOR_MODEL_FILE_NAME);
  }

  @Override
  public ClassifierResult execute(JiraIssue actualIssue) throws URISyntaxException {
    docVectorizer.load(VECTOR_MODEL_FILE_NAME);
    Collection<String> keys = docVectorizer.getNearestLabels(actualIssue.getDescription(), NUMBER_OF_NEAREST_LABELS);
    List<String> labels = new ArrayList<>();
    List<String> users = new ArrayList<>();
    List<String> attachments = new ArrayList<>();

    LOGGER.info("Nearest issues: " + keys);
    keys.forEach((key) -> {
      JiraIssue issue = jiraClient.getIssueByKey(key.trim());
      labels.addAll(issue.getLabels());
      if (issue.getAssignee() != null) {
        users.add(issue.getAssignee().getName());
      }
      if (issue.getReporter() != null) {
        users.add(issue.getReporter().getName());
      }
      issue.getComments().forEach((comment) -> {
        if (comment.getAuthor() != null) {
          users.add(comment.getAuthor().getName());
        }
      });
      issue.getAttachments().forEach((attachment) -> attachments.add(attachment.getFilename()));
    });
    ClassifierResult result = new ClassifierResult();
    result.setIssues(keys);
    result.setLabels(labels);
    result.setUsers(users);
    result.setAttachments(attachments);
    return result;
  }

  @Override
  public Vectorizer getVectorizer() {
    return docVectorizer;
  }

}
