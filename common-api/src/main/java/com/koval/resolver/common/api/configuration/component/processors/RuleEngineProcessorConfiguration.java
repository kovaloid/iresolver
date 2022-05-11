package com.koval.resolver.common.api.configuration.component.processors;


public class RuleEngineProcessorConfiguration {

  private String rulesLocation;
  private boolean autoExecution;

  public String getRulesLocation() {
    return rulesLocation;
  }

  public void setRulesLocation(final String rulesLocation) {
    this.rulesLocation = rulesLocation;
  }

  public boolean isAutoExecution() {
    return autoExecution;
  }

  public void setAutoExecution(final boolean autoExecution) {
    this.autoExecution = autoExecution;
  }

  @Override
  public String toString() {
    return "RuleEngineProcessorConfiguration{"
        + "rulesLocation='" + rulesLocation + '\''
        + "autoExecution='" + autoExecution + '\''
        + '}';
  }
}
