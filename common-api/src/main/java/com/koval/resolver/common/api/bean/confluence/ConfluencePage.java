package com.koval.resolver.common.api.bean.confluence;


public class ConfluencePage {

  private long id;
  private String title;
  private String body;

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(final String body) {
    this.body = body;
  }
}
