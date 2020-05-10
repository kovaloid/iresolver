package com.koval.resolver.common.api.constant;

public enum IssueParts {
  SUMMARY("summary"),
  DESCRIPTION("description"),
  COMMENTS("comments");

  private String content;

  IssueParts(final String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

}
