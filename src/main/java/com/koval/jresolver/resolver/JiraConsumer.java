package com.koval.jresolver.resolver;

import java.util.List;

import com.koval.jresolver.jira.bean.PreparedJiraIssue;

public class JiraConsumer implements Consumer<PreparedJiraIssue> {

  @Override
  public void consume(List<PreparedJiraIssue> data) {
    data.forEach(preparedIssue -> {
      System.out.println("---------------------------------");
      System.out.println(preparedIssue.getUsefulContent());
      System.out.println("---------------------------------");
      System.out.println(preparedIssue.getMostActiveUser().getDisplayName());
      System.out.println("---------------------------------");
    });
  }
}
