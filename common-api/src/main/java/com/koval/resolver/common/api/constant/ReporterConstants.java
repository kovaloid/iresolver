package com.koval.resolver.common.api.constant;

public enum ReporterConstants {
  HTML("html"),
  TEXT("text");

  private String content;

  ReporterConstants(final String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

}
