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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class JiraResolverService {

  //private Consumer<PreparedJiraIssue> consumer;

  //@PostConstruct
  //public void init() {
  //  consumer = new JiraConsumer();
  //}

  //@Async
/*  public void execute(String url, String username, String password) throws URISyntaxException {
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
  }*/

  private AtomicInteger progress = new AtomicInteger();
  private AtomicInteger total = new AtomicInteger();

  @Async
  public void execute(String url, String username, String password) throws URISyntaxException, InterruptedException {


    System.out.println("Start retrieving...");
    JiraClient client = new BasicJiraClient(url, username, password);
    JiraRequestProperties request = new JiraRequestProperties()
      .project("EAS")
      .createdDate(new Date());
    JiraExtractionProperties extraction = new JiraExtractionProperties()
      .maxResults(3)
      .startAt(0)
      .delayAfterEveryMaxResults(5);
    Consumer<PreparedJiraIssue> consumer = new JiraConsumer(progress);

    DataRetriever dataRetriever = new JiraDataRetriever(client, request, extraction, consumer);
    total.set(dataRetriever.getTotal());
    dataRetriever.start();




    /*while (true) {
      Thread.sleep(1000);
      progress.addAndGet(1);
    }*/

  }

  public int getStatus() {
    return progress.get()/total.get();
  }

}
