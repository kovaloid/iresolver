package com.koval.jresolver.common.api.configuration.bean.processors;


public class RuleEngineProcessorConfiguration {

  private String rulesFolder;

  public String getRulesFolder() {
    return rulesFolder;
  }

  public void setRulesFolder(String rulesFolder) {
    this.rulesFolder = rulesFolder;
  }

  @Override
  public String toString() {
    return "RuleEngineProcessorConfiguration{"
        + "rulesFolder='" + rulesFolder + '\''
        + '}';
  }
}
