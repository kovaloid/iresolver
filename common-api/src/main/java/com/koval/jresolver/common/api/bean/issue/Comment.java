package com.koval.jresolver.common.api.bean.issue;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Comment comment = (Comment)o;
    return Objects.equals(author, comment.author)
        && Objects.equals(updateAuthor, comment.updateAuthor)
        && Objects.equals(creationDate, comment.creationDate)
        && Objects.equals(updateDate, comment.updateDate)
        && Objects.equals(body, comment.body);
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, updateAuthor, creationDate, updateDate, body);
  }
}
