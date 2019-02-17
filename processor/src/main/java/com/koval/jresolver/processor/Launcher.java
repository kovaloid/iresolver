package com.koval.jresolver.processor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.koval.jresolver.connector.JiraConnector;
import com.koval.jresolver.connector.configuration.JiraProperties;


public class Launcher {
  public static void main(String[] args) throws InterruptedException, URISyntaxException, IOException {
    JiraConnector jiraConnector = new JiraConnector(new JiraProperties("connector.properties"));
    jiraConnector.getActualIssues();

    List<Processor> processors = new ArrayList<>();
    // processors.add(new SimilarityProcessor());

    prepareAll(processors);
    executeAll(processors);
  }

  private static void prepareAll(List<Processor> processors) throws InterruptedException {
    List<Thread> threads = new ArrayList<>();
    for(Processor processor: processors) {
      threads.add(new Thread(processor::prepare));
    }
    launchProcessors(threads);
  }

  private static void executeAll(List<Processor> processors) throws InterruptedException {
    List<Thread> threads = new ArrayList<>();
    for(Processor processor: processors) {
      threads.add(new Thread(processor::execute));
    }
    launchProcessors(threads);
  }

  private static void launchProcessors(List<Thread> threads) throws InterruptedException {
    for(Thread thread: threads) {
      thread.start();
    }
    for(Thread thread: threads) {
      thread.join();
    }
  }
}
