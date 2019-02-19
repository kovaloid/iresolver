package com.koval.jresolver.processor.similarity.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.atlassian.jira.rest.client.api.domain.Issue;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareListSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.labelaware.LabelAwareSentenceIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.connector.jira.client.JiraClient;
import com.koval.jresolver.connector.jira.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.jira.configuration.ConnectorProperties;
import com.koval.jresolver.processor.similarity.configuration.SimilarityProcessorProperties;
import com.koval.jresolver.processor.similarity.results.SimilarityResult;


public class SimilarityProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimilarityProcessor.class);
  private static final String DATASET_FILE_NAME = "DataSet.txt";
  private static final int NUMBER_OF_NEAREST_LABELS = 10;

  private VectorModelCreator vectorModelCreator;
  private VectorModelSerializer vectorModelSerializer;
  private JiraClient jiraClient;

  public SimilarityProcessor(SimilarityProcessorProperties similarityProcessorProperties) throws URISyntaxException, IOException {
    this.vectorModelCreator = new VectorModelCreator(similarityProcessorProperties);
    this.vectorModelSerializer = new VectorModelSerializer(similarityProcessorProperties);
    this.jiraClient = new BasicJiraClient(new ConnectorProperties().getUrl());
  }

  public void train() throws IOException {
    if (vectorModelSerializer.isSerializedFileExist()) {
      LOGGER.info("Skip classifier configuration. File 'VectorModel.zip' already exists.");
      return;
    }
    VectorModel vectorModel = vectorModelCreator.createFromResource(DATASET_FILE_NAME);
    vectorModelSerializer.serialize(vectorModel);
  }

  public List<SimilarityResult> predict() throws IOException {
    if (!vectorModelSerializer.isSerializedFileExist()) {
      LOGGER.info("Skip prediction phase. File 'VectorModel.zip' does not exist.");
      return new ArrayList<>();
    }
    VectorModel vectorModel = vectorModelSerializer.deserialize(new File("../data/", "VectorModel.zip"));
    return getSimilarIssues(vectorModel);
  }

  private List<SimilarityResult> getSimilarIssues(VectorModel vectorModel) throws IOException {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("UnresolvedDataSet.txt");
    LabelAwareSentenceIterator iterator = new LabelAwareListSentenceIterator(inputStream, "|", 0, 1);
    List<SimilarityResult> result = new ArrayList<>();
    while (iterator.hasNext()) {
      SimilarityResult similarityResult = getSimilarityResult(iterator.nextSentence(), vectorModel);
      result.add(similarityResult);
    }
    return result;
  }


  public SimilarityResult getSimilarityResult(String issueData, VectorModel vectorModel) {
    Collection<String> keys = vectorModel.getNearestLabels(issueData, NUMBER_OF_NEAREST_LABELS);
    List<String> labels = new ArrayList<>();
    List<String> users = new ArrayList<>();
    List<String> attachments = new ArrayList<>();

    LOGGER.info("Nearest issues: " + keys);
    keys.forEach((key) -> {
      Issue issue = jiraClient.getIssueByKey(key.trim());
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
    SimilarityResult result = new SimilarityResult();
    result.setIssues(keys);
    result.setLabels(labels);
    result.setUsers(users);
    result.setAttachments(attachments);
    return result;
  }

}
