package com.koval.jresolver.resolver;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.koval.jresolver.jira.bean.PreparedJiraIssue;
import org.springframework.stereotype.Component;

@Component
public class JiraConsumer implements Consumer<PreparedJiraIssue> {

  private AtomicInteger progress;

  public JiraConsumer(AtomicInteger progress) {
    this.progress = progress;
  }


  @Override
  public void consume(List<PreparedJiraIssue> data) {
    progress.addAndGet(1);
    data.forEach(preparedIssue -> {
      System.out.println("---------------------------------");
      System.out.println(preparedIssue.getUsefulContent());
      System.out.println("---------------------------------");
      System.out.println(preparedIssue.getMostActiveUser().getDisplayName());
      System.out.println("---------------------------------");
    });
  }

  public AtomicInteger getStatus() {
    return progress;
  }

}
