package com.koval.jresolver.common.api.bean.issue;

import org.joda.time.DateTime;

import java.net.URI;
import java.util.List;


public class Issue {

  private URI link;
  private String summary;
  private String description;
  private String resolution;
  private String status;
  private String priority;
  private User reporter;
  private User assignee;
  private IssueType issueType;
  private Project project;
  private DateTime creationDate;
  private DateTime updateDate;
  private DateTime dueDate;
  private List<String> labels;
  private List<Component> components;
  private List<Version> fixVersions;
  private List<Version> affectedVersions;
  private List<Comment> comments;
  private List<IssueLink> issueLinks;
  private List<Attachment> attachments;
  private List<User> watchers;
  private List<SubTask> subTasks;
  private List<IssueField> issueFields;

  public URI getLink() {
    return link;
  }

  public void setLink(URI link) {
    this.link = link;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getResolution() {
    return resolution;
  }

  public void setResolution(String resolution) {
    this.resolution = resolution;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public User getReporter() {
    return reporter;
  }

  public void setReporter(User reporter) {
    this.reporter = reporter;
  }

  public User getAssignee() {
    return assignee;
  }

  public void setAssignee(User assignee) {
    this.assignee = assignee;
  }

  public IssueType getIssueType() {
    return issueType;
  }

  public void setIssueType(IssueType issueType) {
    this.issueType = issueType;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public DateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(DateTime creationDate) {
    this.creationDate = creationDate;
  }

  public DateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(DateTime updateDate) {
    this.updateDate = updateDate;
  }

  public DateTime getDueDate() {
    return dueDate;
  }

  public void setDueDate(DateTime dueDate) {
    this.dueDate = dueDate;
  }

  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  public List<Component> getComponents() {
    return components;
  }

  public void setComponents(List<Component> components) {
    this.components = components;
  }

  public List<Version> getFixVersions() {
    return fixVersions;
  }

  public void setFixVersions(List<Version> fixVersions) {
    this.fixVersions = fixVersions;
  }

  public List<Version> getAffectedVersions() {
    return affectedVersions;
  }

  public void setAffectedVersions(List<Version> affectedVersions) {
    this.affectedVersions = affectedVersions;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public List<IssueLink> getIssueLinks() {
    return issueLinks;
  }

  public void setIssueLinks(List<IssueLink> issueLinks) {
    this.issueLinks = issueLinks;
  }

  public List<Attachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<Attachment> attachments) {
    this.attachments = attachments;
  }

  public List<User> getWatchers() {
    return watchers;
  }

  public void setWatchers(List<User> watchers) {
    this.watchers = watchers;
  }

  public List<SubTask> getSubTasks() {
    return subTasks;
  }

  public void setSubTasks(List<SubTask> subTasks) {
    this.subTasks = subTasks;
  }

  public List<IssueField> getIssueFields() {
    return issueFields;
  }

  public void setIssueFields(List<IssueField> issueFields) {
    this.issueFields = issueFields;
  }
}
