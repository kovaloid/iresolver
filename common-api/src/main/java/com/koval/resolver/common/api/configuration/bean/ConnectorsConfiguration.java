package com.koval.resolver.common.api.configuration.bean;

import com.koval.resolver.common.api.configuration.bean.connectors.BugzillaConnectorConfiguration;
import com.koval.resolver.common.api.configuration.bean.connectors.ConfluenceConnectorConfiguration;
import com.koval.resolver.common.api.configuration.bean.connectors.GitlabConnectorConfiguration;
import com.koval.resolver.common.api.configuration.bean.connectors.JiraConnectorConfiguration;


public class ConnectorsConfiguration {

  private JiraConnectorConfiguration jira;
  private BugzillaConnectorConfiguration bugzilla;
  private ConfluenceConnectorConfiguration confluence;

  private GitlabConnectorConfiguration gitlab;

  public JiraConnectorConfiguration getJira() {
    return jira;
  }

  public void setJira(final JiraConnectorConfiguration jira) {
    this.jira = jira;
  }

  public BugzillaConnectorConfiguration getBugzilla() {
    return bugzilla;
  }

  public void setBugzilla(final BugzillaConnectorConfiguration bugzilla) {
    this.bugzilla = bugzilla;
  }

  public ConfluenceConnectorConfiguration getConfluence() {
    return confluence;
  }

  public void setConfluence(final ConfluenceConnectorConfiguration confluence) {
    this.confluence = confluence;
  }

  public GitlabConnectorConfiguration getGitlab() {
    return gitlab;
  }

  public void setGitlab(GitlabConnectorConfiguration gitlab) {
    this.gitlab = gitlab;
  }


  @Override
  public String toString() {
    return "ConnectorsConfiguration{" +
            "jira=" + jira +
            ", bugzilla=" + bugzilla +
            ", confluence=" + confluence +
            ", gitlab=" + gitlab +
            '}';
  }
}
