package com.koval.jresolver.common.api.bean.result;

import java.util.List;

import com.koval.jresolver.common.api.bean.issue.Attachment;
import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.issue.User;


public class IssueAnalysingResult {

  private Issue originalIssue;
  private List<Pair<Issue, Double>> similarIssues;
  private List<Pair<User, Integer>> qualifiedUsers;
  private List<Pair<String, Integer>> probableLabels;
  private List<Pair<Attachment, Integer>> probableAttachments;
  private List<String> proposals;

  public Issue getOriginalIssue() {
    return originalIssue;
  }

  public void setOriginalIssue(Issue originalIssue) {
    this.originalIssue = originalIssue;
  }

  public List<Pair<Issue, Double>> getSimilarIssues() {
    return similarIssues;
  }

  public void setSimilarIssues(List<Pair<Issue, Double>> similarIssues) {
    this.similarIssues = similarIssues;
  }

  public List<Pair<User, Integer>> getQualifiedUsers() {
    return qualifiedUsers;
  }

  public void setQualifiedUsers(List<Pair<User, Integer>> qualifiedUsers) {
    this.qualifiedUsers = qualifiedUsers;
  }

  public List<Pair<String, Integer>> getProbableLabels() {
    return probableLabels;
  }

  public void setProbableLabels(List<Pair<String, Integer>> probableLabels) {
    this.probableLabels = probableLabels;
  }

  public List<Pair<Attachment, Integer>> getProbableAttachments() {
    return probableAttachments;
  }

  public void setProbableAttachments(List<Pair<Attachment, Integer>> probableAttachments) {
    this.probableAttachments = probableAttachments;
  }

  public List<String> getProposals() {
    return proposals;
  }

  public void setProposals(List<String> proposals) {
    this.proposals = proposals;
  }
}
