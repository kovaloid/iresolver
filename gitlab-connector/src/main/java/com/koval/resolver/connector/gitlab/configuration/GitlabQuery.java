package com.koval.resolver.connector.gitlab.configuration;


public class GitlabQuery {

  private String project;
  private String status;
  private String label;
  private Integer assigneeId;
  private String milestone;
  private Integer authorId;

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Integer getAssigneeId() {
    return assigneeId;
  }

  public void setAssigneeId(Integer assigneeId) {
    this.assigneeId = assigneeId;
  }

  public String getMilestone() {
    return milestone;
  }

  public void setMilestone(String milestone) {
    this.milestone = milestone;
  }

  public Integer getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Integer authorId) {
    this.authorId = authorId;
  }

  @Override
  public String toString() {
    return "GitlabQuery{" +
            "project='" + project + '\'' +
            ", status='" + status + '\'' +
            ", label='" + label + '\'' +
            ", assigneeId='" + assigneeId + '\'' +
            ", milestone='" + milestone + '\'' +
            ", authorId='" + authorId + '\'' +
            '}';
  }
}
