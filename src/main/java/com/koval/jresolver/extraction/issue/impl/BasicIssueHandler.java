package com.koval.jresolver.extraction.issue.impl;

import com.atlassian.jira.rest.client.domain.BasicUser;
import com.atlassian.jira.rest.client.domain.Issue;
import com.koval.jresolver.extraction.issue.IssueHandler;

import java.util.ArrayList;
import java.util.List;


public class BasicIssueHandler implements IssueHandler {



  public void parse(Issue issue) {
    String usefulContent = issue.getSummary() + " " + issue.getDescription() + " " + issue.getFieldByName("steps_to_reproduce");
    BasicUser mostActive = findMostActiveUser(issue);

    //  preparedIssues.add(new PreparedJiraIssue(usefulContent, mostActive));
   // });
   // return preparedIssues;
  }

  private BasicUser findMostActiveUser(Issue issue) {
    List<BasicUser> commentAuthors = new ArrayList<>();
    issue.getComments().forEach(comment -> {
      commentAuthors.add(comment.getAuthor());
    });
    BasicUser mostActive = null;
    if (!commentAuthors.isEmpty()) {
      mostActive = commentAuthors.get(0);
    } else if (issue.getAssignee() != null && issue.getReporter() != null && !issue.getAssignee().getName().equals(issue.getReporter().getName())) {
      mostActive = issue.getAssignee();
    } else if (issue.getReporter() != null) {
      mostActive = issue.getReporter();
    }
    return mostActive;
  }
}
