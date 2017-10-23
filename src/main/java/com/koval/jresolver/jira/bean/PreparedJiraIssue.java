package com.koval.jresolver.jira.bean;

import com.atlassian.jira.rest.client.domain.BasicUser;


public class PreparedJiraIssue {

  private String usefulContent;
  private BasicUser mostActiveUser;

  public PreparedJiraIssue(String usefulContent, BasicUser mostActiveUser) {
    this.usefulContent = usefulContent;
    this.mostActiveUser = mostActiveUser;
  }

  public String getUsefulContent() {
    return usefulContent;
  }

  public BasicUser getMostActiveUser() {
    return mostActiveUser;
  }
}
