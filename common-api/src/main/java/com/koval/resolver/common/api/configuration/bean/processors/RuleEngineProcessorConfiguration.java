package com.koval.resolver.common.api.configuration.bean.processors;


public class RuleEngineProcessorConfiguration {

  private String rulesLocation;

  public String getRulesLocation() {
    return rulesLocation;
  }

  public void setRulesLocation(final String rulesLocation) {
    this.rulesLocation = rulesLocation;
  }

  @Override
  public String toString() {
    return "RuleEngineProcessorConfiguration{"
        + "rulesLocation='" + rulesLocation + '\''
        + '}';
  }
}
