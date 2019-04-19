package com.koval.jresolver.connector.bugzilla;

/*
import b4j.core.DefaultIssue;
import b4j.core.DefaultSearchData;
import b4j.core.Issue;
import b4j.core.session.BugzillaHttpSession;

import java.net.MalformedURLException;
import java.net.URL;

    BugzillaHttpSession session = new BugzillaHttpSession();
    session.setBaseUrl(new URL("https://bz.apache.org/ooo/"));
    session.setBugzillaBugClass(DefaultIssue.class);

    // Open the session
    if (session.open()) {
      // Search abug
      DefaultSearchData searchData = new DefaultSearchData();
      //searchData.add("classification", "Java Projects");
      //searchData.add("product", "Bugzilla for Java");

      searchData.add("offset", "2");
      searchData.add("limit", "2");

      // Perform the search
      Iterable<Issue> i = session.searchBugs(searchData, null);
      for (Issue issue : i) {
        System.out.println("Bug found: "+issue.getId()+" - "+issue.getDescription());
      }

      // Close the session
      session.close();*/


import com.koval.jresolver.common.api.component.connector.Connector;
import com.koval.jresolver.common.api.component.connector.IssueReceiver;


public class BugZillaConnector implements Connector {

  @Override
  public IssueReceiver getResolvedIssuesReceiver() {
    return null;
  }

  @Override
  public IssueReceiver getUnresolvedIssuesReceiver() {
    return null;
  }
}
