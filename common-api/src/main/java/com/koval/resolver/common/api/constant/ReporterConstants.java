package com.koval.resolver.common.api.constant;

public enum ReporterConstants {
  HTML("html"),
  TEXT("text");

  private String content;

  ReporterConstants(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

}
