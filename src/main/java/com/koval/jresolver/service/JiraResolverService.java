package com.koval.jresolver.service;

import com.koval.jresolver.classifier.mlp.ClassesFileCreator;
import com.koval.jresolver.classifier.mlp.DataSetCreator;
import com.koval.jresolver.classifier.mlp.MyClassifier;
import com.koval.jresolver.classifier.mlp.WordVectorizer;
import com.koval.jresolver.connector.client.JiraClient;
import com.koval.jresolver.connector.client.impl.BasicJiraClient;
import com.koval.jresolver.connector.configuration.JiraExtractionProperties;
import com.koval.jresolver.connector.deliver.DataConsumer;
import com.koval.jresolver.connector.deliver.impl.BasicDataConsumer;
import com.koval.jresolver.connector.issue.IssueHandler;
import com.koval.jresolver.connector.issue.impl.BasicIssueHandler;
import com.koval.jresolver.connector.process.DataRetriever;
import com.koval.jresolver.connector.process.impl.BasicDataRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
public class JiraResolverService {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraResolverService.class);
  private DataRetriever dataRetriever;
  private AtomicBoolean isTrainingComplete = new AtomicBoolean(false);

  @PostConstruct
  public void init() throws URISyntaxException {
    LOGGER.info("Initialize JiraResolverService...");
  }

  @Async
  public void execute(String url, String jql, int maxResults, int startAt, int delay) throws URISyntaxException {
    LOGGER.info("Start retrieving...");

    JiraClient jiraClient = new BasicJiraClient(url);

    JiraExtractionProperties jiraExtractionProperties = new JiraExtractionProperties()
      .searchJqlRequest(jql)
      .maxResults(maxResults)
      .startAt(startAt)
      .delayAfterEveryRequest(delay);

    IssueHandler issueHandler = new BasicIssueHandler();
    DataConsumer dataConsumer = new BasicDataConsumer();

    dataRetriever = new BasicDataRetriever(jiraClient, dataConsumer, jiraExtractionProperties);

    dataRetriever.start();
  }

  public void stopExecution() {
    dataRetriever.stop();
  }

  public double getStatus() {
    if (dataRetriever == null) {
      return 0.0;
    }
    return dataRetriever.getStatus();
  }

  @Async
  public void train() {
    isTrainingComplete.set(false);
    WordVectorizer wordVectorizer = new WordVectorizer();
    wordVectorizer.createFromFile("raw.txt");

    ClassesFileCreator classesFileCreator = new ClassesFileCreator(wordVectorizer);
    classesFileCreator.createFilesWithPrefix("classes");

    DataSetCreator dataSetCreator = new DataSetCreator(wordVectorizer);
    dataSetCreator.create("raw.txt", "dataset.csv");

    int batchSizeTraining = 3; //30;
    int batchSizeTest = 5; //44;

    int labelIndex = 0; //4; //index of column with class id
    int classifierInputs = wordVectorizer.getVocabularSortedByIndex().size(); //4; //word vector length
    int classifierOutputs = wordVectorizer.getClasses().get(0).size(); //3; //number of classes

    MyClassifier cl = new MyClassifier(labelIndex, batchSizeTraining, batchSizeTest, classifierInputs, classifierOutputs);
    cl.launch();
    isTrainingComplete.set(true);
  }

  public boolean getTrainStatus() {
    return isTrainingComplete.get();
  }

}
