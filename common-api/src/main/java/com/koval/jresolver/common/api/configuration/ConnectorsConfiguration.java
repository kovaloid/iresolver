package com.koval.jresolver.common.api.configuration;

import java.util.Map;

public class ConnectorsConfiguration {

  private Map<Object, Object> jira;
  private Map<Object, Object> bugzilla;
  private Map<Object, Object> confluence;

  public Map<Object, Object> getJira() {
    return jira;
  }

  public void setJira(Map<Object, Object> jira) {
    this.jira = jira;
  }

  public Map<Object, Object> getBugzilla() {
    return bugzilla;
  }

  public void setBugzilla(Map<Object, Object> bugzilla) {
    this.bugzilla = bugzilla;
  }

  public Map<Object, Object> getConfluence() {
    return confluence;
  }

  public void setConfluence(Map<Object, Object> confluence) {
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
