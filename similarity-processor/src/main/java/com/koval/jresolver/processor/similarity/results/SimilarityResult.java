package com.koval.jresolver.processor.similarity.results;

import java.util.Collection;


public class SimilarityResult {

  private String key;
  private Collection<String> issues;
  private Collection<String> labels;
  private Collection<String> users;
  private Collection<String> attachments;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
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
}
