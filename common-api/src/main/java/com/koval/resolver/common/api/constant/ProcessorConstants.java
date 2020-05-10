package com.koval.resolver.common.api.constant;

public enum ProcessorConstants {
  ISSUES("issues"),
  GRANULAR_ISSUES("granular-issues"),
  DOCUMENTATION("documentation"),
  CONFLUENCE("confluence"),
  RULE_ENGINE("rule-engine");

  private String content;

  ProcessorConstants(final String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }
}
