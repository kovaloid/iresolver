package com.koval.resolver.common.api.configuration.bean;

import com.koval.resolver.common.api.configuration.bean.connectors.BugzillaConnectorConfiguration;
import com.koval.resolver.common.api.configuration.bean.connectors.ConfluenceConnectorConfiguration;
import com.koval.resolver.common.api.configuration.bean.connectors.JiraConnectorConfiguration;


public class ConnectorsConfiguration {

  private JiraConnectorConfiguration jira;
  private BugzillaConnectorConfiguration bugzilla;
  private ConfluenceConnectorConfiguration confluence;

  public JiraConnectorConfiguration getJira() {
    return jira;
  }

  public void setJira(JiraConnectorConfiguration jira) {
    this.jira = jira;
  }

  public BugzillaConnectorConfiguration getBugzilla() {
    return bugzilla;
  }

  public void setBugzilla(BugzillaConnectorConfiguration bugzilla) {
    this.bugzilla = bugzilla;
  }

  public ConfluenceConnectorConfiguration getConfluence() {
    return confluence;
  }

  public void setConfluence(ConfluenceConnectorConfiguration confluence) {
    this.confluence = confluence;
  }

  @Override
  public String toString() {
    return "ConnectorsConfiguration{"
        + "jira=" + jira
        + ", bugzilla=" + bugzilla
        + ", confluence=" + confluence
        + '}';
  }
}
