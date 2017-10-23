package com.koval.jresolver.service;

import com.koval.jresolver.jira.bean.PreparedJiraIssue;
import com.koval.jresolver.jira.client.BasicJiraClient;
import com.koval.jresolver.jira.client.JiraClient;
import com.koval.jresolver.jira.configuration.JiraExtractionProperties;
import com.koval.jresolver.jira.configuration.JiraRequestProperties;
import com.koval.jresolver.jira.process.JiraDataRetriever;
import com.koval.jresolver.resolver.Consumer;
import com.koval.jresolver.resolver.DataRetriever;
import com.koval.jresolver.resolver.JiraConsumer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Date;

@Service
public class JiraResolverService {

  //@Async
  public void execute(String url, String username, String password) throws URISyntaxException {
    System.out.println("Start retrieving...");
    JiraClient client = new BasicJiraClient(url, username, password);
    JiraRequestProperties request = new JiraRequestProperties()
      .project("EAS")
      .createdDate(new Date());
    JiraExtractionProperties extraction = new JiraExtractionProperties()
      .maxResults(3)
      .startAt(0)
      .delayAfterEveryMaxResults(5);
    Consumer<PreparedJiraIssue> consumer = new JiraConsumer();

    DataRetriever dataRetriever = new JiraDataRetriever(client, request, extraction, consumer);
    dataRetriever.start();
  }

}
