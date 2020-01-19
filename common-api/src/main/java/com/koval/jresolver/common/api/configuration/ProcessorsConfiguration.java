package com.koval.jresolver.common.api.configuration;

import java.util.Map;

public class ProcessorsConfiguration {

  private Map<Object, Object> issues;
  private Map<Object, Object> rules;
  private Map<Object, Object> documentation;
  private Map<Object, Object> confluence;

  public Map<Object, Object> getIssues() {
    return issues;
  }

  public void setIssues(Map<Object, Object> issues) {
    this.issues = issues;
  }

  public Map<Object, Object> getRules() {
    return rules;
  }

  public void setRules(Map<Object, Object> rules) {
    this.rules = rules;
  }

  public Map<Object, Object> getDocumentation() {
    return documentation;
  }

  public void setDocumentation(Map<Object, Object> documentation) {
    this.documentation = documentation;
  }

  public Map<Object, Object> getConfluence() {
    return confluence;
  }

  public void setConfluence(Map<Object, Object> confluence) {
    this.confluence = confluence;
  }

  @Override
  public String toString() {
    return "ProcessorsConfiguration{"
        + "issues=" + issues
        + ", rules=" + rules
        + ", documentation=" + documentation
        + ", confluence=" + confluence
        + '}';
  }
}
