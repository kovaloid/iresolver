package com.koval.jresolver.report;

import java.util.Collection;
import java.util.List;

import com.koval.jresolver.classifier.impl.ClassifierResult;
import com.koval.jresolver.connector.bean.JiraIssue;
import com.koval.jresolver.rules.RulesResult;


public class TotalResults {

  private JiraIssue issue;
  private Collection<String> issues;
  private Collection<String> labels;
  private Collection<String> users;
  private Collection<String> attachments;
  private Collection<String> advices;

  public TotalResults(JiraIssue issue, ClassifierResult classifierResult, RulesResult ruleResult) {
    this.issue = issue;
    this.issues = classifierResult.getIssues();
    this.labels = classifierResult.getLabels();
    this.users = classifierResult.getUsers();
    this.attachments = classifierResult.getAttachments();
    this.advices = ruleResult.getAdvices();
  }

  public JiraIssue getIssue() {
    return issue;
  }

  public void setIssue(JiraIssue issue) {
    this.issue = issue;
  }

  public Collection<String> getIssues() {
    return issues;
  }

  public void setIssues(Collection<String> issues) {
    this.issues = issues;
  }

  public Collection<String> getLabels() {
    return labels;
  }

  public void setLabels(Collection<String> labels) {
    this.labels = labels;
  }

  public Collection<String> getUsers() {
    return users;
  }

  public void setUsers(Collection<String> users) {
    this.users = users;
  }

  public Collection<String> getAttachments() {
    return attachments;
  }

  public void setAttachments(Collection<String> attachments) {
    this.attachments = attachments;
  }

  public Collection<String> getAdvices() {
    return advices;
  }

  public void setAdvices(Collection<String> advices) {
    this.advices = advices;
  }
}
