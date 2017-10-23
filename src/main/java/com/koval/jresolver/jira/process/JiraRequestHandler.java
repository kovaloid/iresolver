package com.koval.jresolver.jira.process;

import java.util.ArrayList;
import java.util.List;

import com.atlassian.jira.rest.client.domain.BasicUser;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.koval.jresolver.jira.bean.PreparedJiraIssue;
import com.koval.jresolver.jira.client.JiraClient;


public class JiraRequestHandler {

  private JiraClient client;
  private String jqlRequest;
  private SearchResult searchResult;

  public JiraRequestHandler(JiraClient client, String jqlRequest) {
    this.client = client;
    this.jqlRequest = jqlRequest;
  }

  public List<PreparedJiraIssue> process(int maxResults, int startAt) {
    List<PreparedJiraIssue> preparedIssues = new ArrayList<>(maxResults);
    searchResult = client.getSearchClient().searchJql(jqlRequest, maxResults, startAt, client.getMonitor());
    searchResult.getIssues().forEach(issueDefinition -> {
      Issue issue = client.getIssueClient().getIssue(issueDefinition.getKey(), client.getMonitor());

      String usefulContent = issue.getSummary() + " " + issue.getDescription() + " " + issue.getFieldByName("steps_to_reproduce");
      BasicUser mostActive = findMostActiveUser(issue);

      preparedIssues.add(new PreparedJiraIssue(usefulContent, mostActive));
    });
    return preparedIssues;
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

  public int getTotalResults() {
    return searchResult.getTotal();
  }

}
