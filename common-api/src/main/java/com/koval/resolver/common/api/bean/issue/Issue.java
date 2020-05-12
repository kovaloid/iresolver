package com.koval.resolver.common.api.bean.issue;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.joda.time.DateTime;


@SuppressWarnings("PMD.TooManyFields")
public class Issue {

  private URI link;
  private String key;
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
  private List<SubTask> subTasks;
  private List<IssueField> issueFields;

  public URI getLink() {
    return link;
  }

  public void setLink(final URI link) {
    this.link = link;
  }

  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(final String summary) {
    this.summary = summary;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getResolution() {
    return resolution;
  }

  public void setResolution(final String resolution) {
    this.resolution = resolution;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(final String status) {
    this.status = status;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(final String priority) {
    this.priority = priority;
  }

  public User getReporter() {
    return reporter;
  }

  public void setReporter(final User reporter) {
    this.reporter = reporter;
  }

  public User getAssignee() {
    return assignee;
  }

  public void setAssignee(final User assignee) {
    this.assignee = assignee;
  }

  public IssueType getIssueType() {
    return issueType;
  }

  public void setIssueType(final IssueType issueType) {
    this.issueType = issueType;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(final Project project) {
    this.project = project;
  }

  public DateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(final DateTime creationDate) {
    this.creationDate = creationDate;
  }

  public DateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(final DateTime updateDate) {
    this.updateDate = updateDate;
  }

  public DateTime getDueDate() {
    return dueDate;
  }

  public void setDueDate(final DateTime dueDate) {
    this.dueDate = dueDate;
  }

  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(final List<String> labels) {
    this.labels = labels;
  }

  public List<Component> getComponents() {
    return components;
  }

  public void setComponents(final List<Component> components) {
    this.components = components;
  }

  public List<Version> getFixVersions() {
    return fixVersions;
  }

  public void setFixVersions(final List<Version> fixVersions) {
    this.fixVersions = fixVersions;
  }

  public List<Version> getAffectedVersions() {
    return affectedVersions;
  }

  public void setAffectedVersions(final List<Version> affectedVersions) {
    this.affectedVersions = affectedVersions;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(final List<Comment> comments) {
    this.comments = comments;
  }

  public List<IssueLink> getIssueLinks() {
    return issueLinks;
  }

  public void setIssueLinks(final List<IssueLink> issueLinks) {
    this.issueLinks = issueLinks;
  }

  public List<Attachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(final List<Attachment> attachments) {
    this.attachments = attachments;
  }

  public List<SubTask> getSubTasks() {
    return subTasks;
  }

  public void setSubTasks(final List<SubTask> subTasks) {
    this.subTasks = subTasks;
  }

  public List<IssueField> getIssueFields() {
    return issueFields;
  }

  public void setIssueFields(final List<IssueField> issueFields) {
    this.issueFields = issueFields;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Issue issue = (Issue)o;
    return Objects.equals(link, issue.link)
        && Objects.equals(key, issue.key)
        && Objects.equals(summary, issue.summary)
        && Objects.equals(description, issue.description)
        && Objects.equals(resolution, issue.resolution)
        && Objects.equals(status, issue.status)
        && Objects.equals(priority, issue.priority)
        && Objects.equals(reporter, issue.reporter)
        && Objects.equals(assignee, issue.assignee)
        && Objects.equals(issueType, issue.issueType)
        && Objects.equals(project, issue.project)
        && Objects.equals(creationDate, issue.creationDate)
        && Objects.equals(updateDate, issue.updateDate)
        && Objects.equals(dueDate, issue.dueDate)
        && Objects.equals(labels, issue.labels)
        && Objects.equals(components, issue.components)
        && Objects.equals(fixVersions, issue.fixVersions)
        && Objects.equals(affectedVersions, issue.affectedVersions)
        && Objects.equals(comments, issue.comments)
        && Objects.equals(issueLinks, issue.issueLinks)
        && Objects.equals(attachments, issue.attachments)
        && Objects.equals(subTasks, issue.subTasks)
        && Objects.equals(issueFields, issue.issueFields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(link, key, summary, description, resolution, status, priority, reporter, assignee, issueType, project, creationDate, updateDate, dueDate, labels, components, fixVersions, affectedVersions, comments, issueLinks, attachments, subTasks, issueFields);
  }
}
