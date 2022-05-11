package com.koval.resolver.common.api.util;

import java.util.List;

import com.koval.resolver.common.api.model.issue.Comment;
import com.koval.resolver.common.api.model.issue.Issue;
import com.koval.resolver.common.api.model.issue.User;


public final class RuleUtil {

  private RuleUtil() {
  }

  public static void changeStatus(final Issue issue, final String status) {
    issue.setStatus(status);
  }

  public static void writeComment(final Issue issue, final String commentBody) {
    List<Comment> comments = issue.getComments();
    Comment newComment = new Comment();
    newComment.setBody(commentBody);
    comments.add(newComment);
    issue.setComments(comments);
  }

  public static void addLabel(final Issue issue, final String label) {
    List<String> labels = issue.getLabels();
    labels.add(label);
    issue.setLabels(labels);
  }

  public static void setAssignee(final Issue issue, final String assignee) {
    User user = new User();
    user.setName(assignee);
    issue.setAssignee(user);
  }

  public static void setPriority(final Issue issue, final String priority) {
    issue.setPriority(priority);
  }
}
