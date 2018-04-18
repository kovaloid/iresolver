package com.koval.jresolver.classifier.impl;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.classifier.Classifier;
import com.koval.jresolver.classifier.impl.ClassifierResult;
import com.koval.jresolver.classifier.impl.DocVectorizer;
import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.client.JiraClient;
import com.koval.jresolver.connector.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.configuration.JiraProperties;


public class Doc2vecClassifier implements Classifier {

  private static final String DATASET_FILE_NAME = "dataset.txt";
  private static final String VECTOR_MODEL_FILE_NAME = "vectors.model";
  private static final int NUMBER_OF_NEAREST_LABELS = 10;

  private DocVectorizer docVectorizer = new DocVectorizer();
  private JiraConnector jiraConnector;
  private JiraClient jiraClient;

  public Doc2vecClassifier() throws URISyntaxException {
	  JiraProperties jiraProperties = new JiraProperties("connector.properties");
	  jiraConnector = new JiraConnector(jiraProperties);
	  jiraClient = new BasicJiraClient(jiraProperties.getUrl());
  }

  @Override
  public void configure() {
	  jiraConnector.createHistoryIssuesDataset(DATASET_FILE_NAME);
	  docVectorizer.createFromDataset(DATASET_FILE_NAME);
	  docVectorizer.save(VECTOR_MODEL_FILE_NAME);
  }

  @Override
  public ClassifierResult execute(Issue actualIssue) throws URISyntaxException {
	docVectorizer.load(VECTOR_MODEL_FILE_NAME);
    Collection<String> keys = docVectorizer.getNearestLabels(actualIssue.getDescription(), NUMBER_OF_NEAREST_LABELS);
    List<String> labels = new ArrayList<>();
    List<String> users = new ArrayList<>();
    List<String> attachments = new ArrayList<>();
    keys.forEach((key) -> {
      Issue issue = jiraClient.getIssueByKey(key);
      labels.addAll(issue.getLabels());
      users.add(issue.getAssignee().getName());
      users.add(issue.getReporter().getName());
      issue.getComments().forEach((comment) -> {
    	  users.add(comment.getAuthor().getName());
      });
      issue.getAttachments().forEach((attachment) -> {
    	  attachments.add(attachment.getFilename());
      });
    });
    ClassifierResult result = new ClassifierResult();
    result.setIssues(keys);
    result.setLabels(labels);
    result.setUsers(users);
    result.setAttachments(attachments);
    return result;
  }

}
