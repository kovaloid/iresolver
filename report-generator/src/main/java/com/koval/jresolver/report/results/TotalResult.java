package com.koval.jresolver.report.results;

import java.util.HashSet;
import java.util.Set;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.koval.jresolver.rules.results.RulesResult;


public class TotalResult {

  private Issue issue;
  private Set<String> issues;
  private Set<String> labels;
  private Set<String> users;
  private Set<String> attachments;
  private Set<String> advices;

  /*public TotalResult(Issue issue, ClassifierResult classifierResult, RulesResult ruleResult) {
    this.issue = issue;
    this.issues = new HashSet<>(classifierResult.getIssues());
    this.labels = new HashSet<>(classifierResult.getLabels());
    this.users = new HashSet<>(classifierResult.getUsers());
    this.attachments = new HashSet<>(classifierResult.getAttachments());
    this.advices = new HashSet<>(ruleResult.getAdvices());
  }*/

  public Issue getIssue() {
    return issue;
  }

  public void setIssue(Issue issue) {
    this.issue = issue;
  }

  public Set<String> getIssues() {
    return issues;
  }

  public void setIssues(Set<String> issues) {
    this.issues = issues;
  }

  public Set<String> getLabels() {
    return labels;
  }

  public void setLabels(Set<String> labels) {
    this.labels = labels;
  }

  public Set<String> getUsers() {
    return users;
  }

  public void setUsers(Set<String> users) {
    this.users = users;
  }

  public Set<String> getAttachments() {
    return attachments;
  }

  public void setAttachments(Set<String> attachments) {
    this.attachments = attachments;
  }

  public Set<String> getAdvices() {
    return advices;
  }

  public void setAdvices(Set<String> advices) {
    this.advices = advices;
  }
}
