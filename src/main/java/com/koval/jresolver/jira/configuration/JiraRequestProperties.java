package com.koval.jresolver.jira.configuration;

import java.util.Date;


public class JiraRequestProperties {

  private String projectName = "";
  private Date createdDate;

  public JiraRequestProperties project(String projectName) {
    this.projectName = projectName;
    return this;
  }

  public JiraRequestProperties createdDate(Date createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  public String getProjectName() {
    return projectName;
  }

  public Date getCreatedDate() {
    return createdDate;
  }
}
