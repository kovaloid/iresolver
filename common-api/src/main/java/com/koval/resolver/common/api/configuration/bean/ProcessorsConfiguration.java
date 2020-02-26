package com.koval.resolver.common.api.configuration.bean;

import com.koval.resolver.common.api.configuration.bean.processors.ConfluenceProcessorConfiguration;
import com.koval.resolver.common.api.configuration.bean.processors.DocumentationProcessorConfiguration;
import com.koval.resolver.common.api.configuration.bean.processors.GranularIssuesProcessorConfiguration;
import com.koval.resolver.common.api.configuration.bean.processors.IssuesProcessorConfiguration;
import com.koval.resolver.common.api.configuration.bean.processors.RuleEngineProcessorConfiguration;


public class ProcessorsConfiguration {

  private IssuesProcessorConfiguration issues;
  private GranularIssuesProcessorConfiguration granularIssues;
  private RuleEngineProcessorConfiguration ruleEngine;
  private DocumentationProcessorConfiguration documentation;
  private ConfluenceProcessorConfiguration confluence;

  public IssuesProcessorConfiguration getIssues() {
    return issues;
  }

  public void setIssues(IssuesProcessorConfiguration issues) {
    this.issues = issues;
  }

  public GranularIssuesProcessorConfiguration getGranularIssues() {
    return granularIssues;
  }

  public void setGranularIssues(GranularIssuesProcessorConfiguration granularIssues) {
    this.granularIssues = granularIssues;
  }

  public RuleEngineProcessorConfiguration getRuleEngine() {
    return ruleEngine;
  }

  public void setRuleEngine(RuleEngineProcessorConfiguration ruleEngine) {
    this.ruleEngine = ruleEngine;
  }

  public DocumentationProcessorConfiguration getDocumentation() {
    return documentation;
  }

  public void setDocumentation(DocumentationProcessorConfiguration documentation) {
    this.documentation = documentation;
  }

  public ConfluenceProcessorConfiguration getConfluence() {
    return confluence;
  }

  public void setConfluence(ConfluenceProcessorConfiguration confluence) {
    this.confluence = confluence;
  }

  @Override
  public String toString() {
    return "ProcessorsConfiguration{"
        + "issues=" + issues
        + ", granularIssues=" + granularIssues
        + ", ruleEngine=" + ruleEngine
        + ", documentation=" + documentation
        + ", confluence=" + confluence
        + '}';
  }
}
