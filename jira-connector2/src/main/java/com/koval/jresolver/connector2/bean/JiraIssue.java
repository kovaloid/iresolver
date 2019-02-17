package com.koval.jresolver.connector2.bean;

import com.atlassian.jira.rest.client.api.domain.Attachment;
import com.atlassian.jira.rest.client.api.domain.BasicComponent;
import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.BasicUser;
import com.atlassian.jira.rest.client.api.domain.BasicVotes;
import com.atlassian.jira.rest.client.api.domain.BasicWatchers;
import com.atlassian.jira.rest.client.api.domain.ChangelogGroup;
import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.IssueLink;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.Subtask;
import com.atlassian.jira.rest.client.api.domain.TimeTracking;
import com.atlassian.jira.rest.client.api.domain.Version;
import com.atlassian.jira.rest.client.api.domain.Worklog;
import com.atlassian.jira.rest.client.api.domain.Status;
import com.atlassian.jira.rest.client.api.domain.Resolution;
import org.joda.time.DateTime;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("PMD.TooManyFields")
public class JiraIssue {

  private String key;
  private Long id;
  private URI self;
  private Status status;
  private IssueType issueType;
  private BasicProject project;
  private URI transitionsUri;
  private Collection<String> expandos;
  private Collection<BasicComponent> components;
  private String summary;
  private String description;
  private BasicUser reporter;
  private BasicUser assignee;
  private Resolution resolution;
  private Collection<IssueField> fields;
  private DateTime creationDate;
  private DateTime updateDate;
  private DateTime dueDate;
  private BasicPriority priority;
  private BasicVotes votes;
  private Collection<Version> fixVersions;
  private Collection<Version> affectedVersions;
  private Collection<Comment> comments;
  private Collection<IssueLink> issueLinks;
  private Collection<Attachment> attachments;
  private Collection<Worklog> worklogs;
  private BasicWatchers watchers;
  private TimeTracking timeTracking;
  private Collection<Subtask> subtasks;
  private Collection<ChangelogGroup> changelog;
  private Set<String> labels;
  private Map<String, IssueField> extraFields;

  public JiraIssue() {
  }

  public JiraIssue(Issue issue) {
    this.key = issue.getKey();
    this.id = issue.getId();
    this.self = issue.getSelf();
    this.status = issue.getStatus();
    this.issueType = issue.getIssueType();
    this.project = issue.getProject();
    this.transitionsUri = issue.getTransitionsUri();
    this.expandos = toCollection(issue.getExpandos());
    this.components = toCollection(issue.getComponents());
    this.summary = issue.getSummary();
    this.description = issue.getDescription();
    this.reporter = issue.getReporter();
    this.assignee = issue.getAssignee();
    this.resolution = issue.getResolution();
    this.fields = toCollection(issue.getFields());
    this.creationDate = issue.getCreationDate();
    this.updateDate = issue.getUpdateDate();
    this.dueDate = issue.getDueDate();
    this.priority = issue.getPriority();
    this.votes = issue.getVotes();
    this.fixVersions = toCollection(issue.getFixVersions());
    this.affectedVersions = toCollection(issue.getAffectedVersions());
    this.comments = toCollection(issue.getComments());
    this.issueLinks = toCollection(issue.getIssueLinks());
    this.attachments = toCollection(issue.getAttachments());
    this.worklogs = toCollection(issue.getWorklogs());
    this.watchers = issue.getWatchers();
    this.timeTracking = issue.getTimeTracking();
    this.subtasks = toCollection(issue.getSubtasks());
    this.changelog = toCollection(issue.getChangelog());
    this.labels = issue.getLabels();
  }

  private <T> Collection<T> toCollection(final Iterable<T> iterable) {
    if (iterable == null) {
      return null;
    }
    Collection<T> result = new ArrayList<>();
    iterable.forEach(result::add);
    return result;
  }

  public void addExtraFields(Issue issue, Collection<String> fields) {
    for (String fieldName: fields) {
      extraFields.put(fieldName, issue.getFieldByName(fieldName));
    }
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public URI getSelf() {
    return self;
  }

  public void setSelf(URI self) {
    this.self = self;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public IssueType getIssueType() {
    return issueType;
  }

  public void setIssueType(IssueType issueType) {
    this.issueType = issueType;
  }

  public BasicProject getProject() {
    return project;
  }

  public void setProject(BasicProject project) {
    this.project = project;
  }

  public URI getTransitionsUri() {
    return transitionsUri;
  }

  public void setTransitionsUri(URI transitionsUri) {
    this.transitionsUri = transitionsUri;
  }

  public Collection<String> getExpandos() {
    return expandos;
  }

  public void setExpandos(Collection<String> expandos) {
    this.expandos = expandos;
  }

  public Collection<BasicComponent> getComponents() {
    return components;
  }

  public void setComponents(Collection<BasicComponent> components) {
    this.components = components;
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

  public BasicUser getReporter() {
    return reporter;
  }

  public void setReporter(BasicUser reporter) {
    this.reporter = reporter;
  }

  public BasicUser getAssignee() {
    return assignee;
  }

  public void setAssignee(BasicUser assignee) {
    this.assignee = assignee;
  }

  public Resolution getResolution() {
    return resolution;
  }

  public void setResolution(Resolution resolution) {
    this.resolution = resolution;
  }

  public Collection<IssueField> getFields() {
    return fields;
  }

  public void setFields(Collection<IssueField> fields) {
    this.fields = fields;
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

  public BasicPriority getPriority() {
    return priority;
  }

  public void setPriority(BasicPriority priority) {
    this.priority = priority;
  }

  public BasicVotes getVotes() {
    return votes;
  }

  public void setVotes(BasicVotes votes) {
    this.votes = votes;
  }

  public Collection<Version> getFixVersions() {
    return fixVersions;
  }

  public void setFixVersions(Collection<Version> fixVersions) {
    this.fixVersions = fixVersions;
  }

  public Collection<Version> getAffectedVersions() {
    return affectedVersions;
  }

  public void setAffectedVersions(Collection<Version> affectedVersions) {
    this.affectedVersions = affectedVersions;
  }

  public Collection<Comment> getComments() {
    return comments;
  }

  public void setComments(Collection<Comment> comments) {
    this.comments = comments;
  }

  public Collection<IssueLink> getIssueLinks() {
    return issueLinks;
  }

  public void setIssueLinks(Collection<IssueLink> issueLinks) {
    this.issueLinks = issueLinks;
  }

  public Collection<Attachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(Collection<Attachment> attachments) {
    this.attachments = attachments;
  }

  public Collection<Worklog> getWorklogs() {
    return worklogs;
  }

  public void setWorklogs(Collection<Worklog> worklogs) {
    this.worklogs = worklogs;
  }

  public BasicWatchers getWatchers() {
    return watchers;
  }

  public void setWatchers(BasicWatchers watchers) {
    this.watchers = watchers;
  }

  public TimeTracking getTimeTracking() {
    return timeTracking;
  }

  public void setTimeTracking(TimeTracking timeTracking) {
    this.timeTracking = timeTracking;
  }

  public Collection<Subtask> getSubtasks() {
    return subtasks;
  }

  public void setSubtasks(Collection<Subtask> subtasks) {
    this.subtasks = subtasks;
  }

  public Collection<ChangelogGroup> getChangelog() {
    return changelog;
  }

  public void setChangelog(Collection<ChangelogGroup> changelog) {
    this.changelog = changelog;
  }

  public Set<String> getLabels() {
    return labels;
  }

  public void setLabels(Set<String> labels) {
    this.labels = labels;
  }
}
