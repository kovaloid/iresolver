package com.koval.jresolver.processor.similarity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.domain.Attachment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;
import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.processor.api.Processor;
import com.koval.jresolver.processor.result.IssueProcessingResult;
import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;
import com.koval.jresolver.processor.similarity.core.model.VectorModel;
import com.koval.jresolver.processor.similarity.core.model.VectorModelSerializer;


public class SimilarityProcessor implements Processor {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimilarityProcessor.class);
  private static final int NUMBER_OF_NEAREST_LABELS = 10;

  private final JiraClient jiraClient;
  private final VectorModel vectorModel;

  public SimilarityProcessor(JiraClient jiraClient, SimilarityProcessorProperties properties) throws IOException {
    this.jiraClient = jiraClient;
    VectorModelSerializer vectorModelSerializer = new VectorModelSerializer(properties);
    File vectorModelFile = new File(properties.getWorkFolder(), properties.getVectorModelFileName());
    this.vectorModel = vectorModelSerializer.deserialize(vectorModelFile);
  }

  @Override
  public void run(Issue issue, IssueProcessingResult result) {
    Collection<String> keys = vectorModel.getNearestLabels(issue.getDescription(), NUMBER_OF_NEAREST_LABELS);
    List<Issue> issues = new ArrayList<>();
    List<String> labels = new ArrayList<>();
    List<User> users = new ArrayList<>();
    List<Attachment> attachments = new ArrayList<>();

    LOGGER.info("Nearest issues: " + keys);
    keys.forEach((key) -> {
      Issue relatedIssue = jiraClient.getIssueByKey(key.trim());
      issues.add(relatedIssue);
      labels.addAll(relatedIssue.getLabels());
      if (relatedIssue.getAssignee() != null) {
        users.add(relatedIssue.getAssignee());
      }
      if (relatedIssue.getReporter() != null) {
        users.add(relatedIssue.getReporter());
      }
      relatedIssue.getComments().forEach((comment) -> {
        // if (comment.getAuthor() != null) {
          /* BasicUser author = comment.getAuthor();
          users.add(new User(author.getSelf(), author.getName(), author.getDisplayName(), "",
              new ExpandableProperty<>(0), new HashMap<>(), "")); */
        // }
      });
      relatedIssue.getAttachments().forEach(attachments::add);
    });
    result.setIssue(issue);
    result.setIssues(issues);
    result.setAttachments(attachments);
    result.setUsers(users);
    result.setLabels(labels);
  }
}
