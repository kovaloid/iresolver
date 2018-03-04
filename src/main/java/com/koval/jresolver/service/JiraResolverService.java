package com.koval.jresolver.service;

import com.koval.jresolver.extraction.client.JiraClient;
import com.koval.jresolver.extraction.client.impl.BasicJiraClient;
import com.koval.jresolver.extraction.configuration.JiraExtractionProperties;
import com.koval.jresolver.extraction.deliver.DataConsumer;
import com.koval.jresolver.extraction.deliver.impl.BasicDataConsumer;
import com.koval.jresolver.extraction.issue.IssueHandler;
import com.koval.jresolver.extraction.issue.impl.BasicIssueHandler;
import com.koval.jresolver.extraction.process.DataRetriever;
import com.koval.jresolver.extraction.process.impl.BasicDataRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;


@Service
public class JiraResolverService {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraResolverService.class);
  private DataRetriever dataRetriever;

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

  public double getStatus() {
    if (dataRetriever == null) {
      return 0.0;
    }
    return dataRetriever.getStatus();
  }

}
