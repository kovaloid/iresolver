package com.koval.jresolver.connector.bugzilla.client;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.bean.issue.Issue;
import com.koval.jresolver.common.api.bean.issue.IssueField;
import com.koval.jresolver.common.api.component.connector.IssueClient;
import com.koval.jresolver.common.api.component.connector.IssueTransformer;
import com.koval.jresolver.common.api.util.CollectionsUtil;

import b4j.core.DefaultSearchData;
import b4j.core.session.BugzillaHttpSession;


public class BugZillaIssueClient implements IssueClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(BugZillaIssueClient.class);

  private final BugzillaHttpSession session;
  private final IssueTransformer<b4j.core.Issue> issueTransformer;

  BugZillaIssueClient(BugzillaHttpSession session) {
    this.session = session;
    this.issueTransformer = new BugZillaIssueTransformer();
    session.open();
  }

  @Override
  public int getTotalIssues(String query) {
    return 0;
  }

  @Override
  public List<Issue> search(String query, int maxResults, int startAt, List<String> fields) {
    LOGGER.debug("Send search request: Query = '{}' MaxResults = '{}' StartAt = '{}'.", query, maxResults, startAt);
    DefaultSearchData searchData = new DefaultSearchData();
    //searchData.add("classification", "Java Projects");
    //searchData.add("product", "Bugzilla for Java");
    searchData.add("offset", String.valueOf(startAt));
    searchData.add("limit", String.valueOf(maxResults));

    // Perform the search
    Iterable<b4j.core.Issue> i = session.searchBugs(searchData, null);
    for (b4j.core.Issue issue : i) {
      LOGGER.info("Bug found: " + issue.getId() + " - " + issue.getDescription());
    }
    return issueTransformer.transform(CollectionsUtil.convert(i));
  }

  @Override
  public Issue getIssueByKey(String issueKey) {
    return null;
  }

  @Override
  public List<IssueField> getIssueFields() {
    return null;
  }

  @Override
  public void close() throws IOException {
    session.close();
  }
}
