package com.koval.resolver.connector.bugzilla.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.resolver.common.api.bean.issue.Issue;
import com.koval.resolver.common.api.bean.issue.IssueField;
import com.koval.resolver.common.api.component.connector.IssueClient;
import com.koval.resolver.common.api.component.connector.IssueTransformer;
import com.koval.resolver.connector.bugzilla.configuration.BugzillaQuery;
import com.koval.resolver.connector.bugzilla.configuration.BugzillaQueryParser;

import b4j.core.DefaultSearchData;
import b4j.core.session.BugzillaHttpSession;


public class BugzillaIssueClient implements IssueClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(BugzillaIssueClient.class);

  private final BugzillaHttpSession session;
  private final IssueTransformer<b4j.core.Issue> issueTransformer;

  BugzillaIssueClient(final BugzillaHttpSession session) {
    this.session = session;
    this.issueTransformer = new BugzillaIssueTransformer();
    session.open();
  }

  @Override
  public int getTotalIssues(final String query) {
    final DefaultSearchData searchData = getSearchDataByQuery(query);
    searchData.add("offset", "0");
    searchData.add("limit", "0");
    AtomicInteger totalIssues = new AtomicInteger();
    session.searchBugs(searchData, totalIssues::set);
    return totalIssues.get();
  }

  @Override
  public List<Issue> search(final String query, final int maxResults, final int startAt, final List<String> fields) {
    LOGGER.debug("Send search request: Query = '{}' MaxResults = '{}' StartAt = '{}'.", query, maxResults, startAt);
    final DefaultSearchData searchData = getSearchDataByQuery(query);

    searchData.add("offset", String.valueOf(startAt));
    searchData.add("limit", String.valueOf(maxResults));

    final Iterable<b4j.core.Issue> issues = session.searchBugs(searchData, null);
    final List<b4j.core.Issue> rawIssueList = new ArrayList<>();
    for (final b4j.core.Issue issue : issues) {
      LOGGER.info("Bug found: " + issue.getId() + " - " + issue.getSummary());
      rawIssueList.add(issue);
    }
    return issueTransformer.transform(rawIssueList);
  }

  private DefaultSearchData getSearchDataByQuery(final String query) {
    final DefaultSearchData searchData = new DefaultSearchData();

    final BugzillaQueryParser queryParser = new BugzillaQueryParser();
    final BugzillaQuery parsedQuery = queryParser.parse(query);
    if (parsedQuery.getAssignee() != null) {
      searchData.add("assignee", parsedQuery.getAssignee());
    }
    if (parsedQuery.getReporter() != null) {
      searchData.add("reporter", parsedQuery.getReporter());
    }
    if (parsedQuery.getProduct() != null) {
      searchData.add("product", parsedQuery.getProduct());
    }
    if (parsedQuery.getStatus() != null) {
      String bugzillaStatus = getBugzillaStatus(parsedQuery.getStatus());
      searchData.add("bug_status", bugzillaStatus);
    }
    if (parsedQuery.getResolution() != null) {
      searchData.add("resolution", parsedQuery.getResolution());
    }
    if (parsedQuery.getPriority() != null) {
      searchData.add("priority", parsedQuery.getPriority());
    }
    if (parsedQuery.getVersion() != null) {
      searchData.add("version", parsedQuery.getVersion());
    }

    return searchData;
  }

  private String getBugzillaStatus(final String status) {
    String bugzillaStatus = "";
    if ("Close".equals(status)) {
      bugzillaStatus = "__closed__";
    } else if ("Open".equals(status)) {
      bugzillaStatus = "__open__";
    }
    return bugzillaStatus;
  }

  @Override
  public Issue getIssueByKey(final String issueKey) {
    return issueTransformer.transform(session.getIssue(issueKey));
  }

  @Override
  public List<IssueField> getIssueFields() {
    final DefaultSearchData searchData = new DefaultSearchData();
    searchData.add("offset", "0");
    searchData.add("limit", "1");
    final Iterator<b4j.core.Issue> issues = session.searchBugs(searchData, null).iterator();
    final List<IssueField> issueFields = new ArrayList<>();
    if (issues.hasNext()) {
      issues.next().getCustomFieldNames().forEach((fieldName) -> issueFields.add(new IssueField(fieldName)));
    }
    return issueFields;
  }

  @Override
  public void close() throws IOException {
    session.close();
  }
}
