package com.koval.jresolver.classifier.core.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.classifier.configuration.ClassifierProperties;
import com.koval.jresolver.classifier.core.Classifier;
import com.koval.jresolver.classifier.core.Vectorizer;
import com.koval.jresolver.classifier.results.ClassifierResult;
import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.connector.client.JiraClient;
import com.koval.jresolver.connector.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.configuration.JiraProperties;
import com.koval.jresolver.manager.Manager;


public class DocClassifier implements Classifier {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocClassifier.class);
  private static final int NUMBER_OF_NEAREST_LABELS = 10;

  private DocVectorizer docVectorizer;
  private JiraConnector jiraConnector;
  private JiraClient jiraClient;

  public DocClassifier(ClassifierProperties classifierProperties) throws URISyntaxException, IOException {
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    init(jiraProperties, classifierProperties);
  }

  public DocClassifier(ClassifierProperties classifierProperties, String password) throws URISyntaxException, IOException {
    JiraProperties jiraProperties = new JiraProperties("connector.properties");
    jiraProperties.setPassword(password);
    init(jiraProperties, classifierProperties);
  }

  private void init(JiraProperties jiraProperties, ClassifierProperties classifierProperties) throws URISyntaxException {
    this.jiraConnector = new JiraConnector(jiraProperties);
    this.jiraClient = new BasicJiraClient(jiraProperties.getUrl());
    this.docVectorizer = new DocVectorizer(classifierProperties);
  }

  @Override
  public void prepare() throws IOException {
    if (Manager.getDataSet().exists()) {
      LOGGER.info("Skip classifier preparation. File 'DataSet.txt' is already exists.");
      return;
    }
    jiraConnector.createHistoryIssuesDataSet();
  }

  @Override
  public void configure() throws IOException {
    if (Manager.getVectorModel().exists()) {
      LOGGER.info("Skip classifier configuration. File 'VectorModel.zip' is already exists.");
      return;
    }
    docVectorizer.createFromDataset();
    docVectorizer.save();
  }

  @Override
  public ClassifierResult execute(JiraIssue actualIssue) {
    docVectorizer.load();
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
