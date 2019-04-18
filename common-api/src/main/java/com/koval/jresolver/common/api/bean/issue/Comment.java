package com.koval.jresolver.common.api.bean.issue;

import org.joda.time.DateTime;


public class Comment {

  private User author;
  private User updateAuthor;
  private DateTime creationDate;
  private DateTime updateDate;
  private String body;

  public Comment() {
  }

  public Comment(User author, User updateAuthor, DateTime creationDate, DateTime updateDate, String body) {
    this.author = author;
    this.updateAuthor = updateAuthor;
    this.creationDate = creationDate;
    this.updateDate = updateDate;
    this.body = body;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public User getUpdateAuthor() {
    return updateAuthor;
  }

  public void setUpdateAuthor(User updateAuthor) {
    this.updateAuthor = updateAuthor;
  }

  public DateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(DateTime creationDate) {
    this.creationDate = creationDate;
  }

  public DateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(DateTime updateDate) {
    this.updateDate = updateDate;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
