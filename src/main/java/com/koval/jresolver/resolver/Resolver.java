package com.koval.jresolver.resolver;

import java.net.URISyntaxException;
import java.util.Date;

import com.koval.jresolver.jira.bean.PreparedJiraIssue;
import com.koval.jresolver.jira.client.BasicJiraClient;
import com.koval.jresolver.jira.client.JiraClient;
import com.koval.jresolver.jira.configuration.JiraExtractionProperties;
import com.koval.jresolver.jira.configuration.JiraRequestProperties;
import com.koval.jresolver.jira.process.JiraDataRetriever;

public class Resolver {
  public static void main(String[] args) throws URISyntaxException {
    JiraClient client = new BasicJiraClient(args[0], args[1], args[2]);
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

    // dataRetriever.getStatus();
    // dataRetriever.stop();
  }
}
