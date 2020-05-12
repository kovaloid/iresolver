package com.koval.resolver.common.api.bean.issue;

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

  public Comment(
    final User author, final User updateAuthor, final DateTime creationDate, final DateTime updateDate,
    final String body) {
    this.author = author;
    this.updateAuthor = updateAuthor;
    this.creationDate = creationDate;
    this.updateDate = updateDate;
    this.body = body;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(final User author) {
    this.author = author;
  }

  public User getUpdateAuthor() {
    return updateAuthor;
  }

  public void setUpdateAuthor(final User updateAuthor) {
    this.updateAuthor = updateAuthor;
  }

  public DateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(final DateTime creationDate) {
    this.creationDate = creationDate;
  }

  public DateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(final DateTime updateDate) {
    this.updateDate = updateDate;
  }

  public String getBody() {
    return body;
  }

  public void setBody(final String body) {
    this.body = body;
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, updateAuthor, creationDate, updateDate, body);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Comment comment = (Comment)o;
    return Objects.equals(author, comment.author)
           && Objects.equals(updateAuthor, comment.updateAuthor)
           && Objects.equals(creationDate, comment.creationDate)
           && Objects.equals(updateDate, comment.updateDate)
           && Objects.equals(body, comment.body);
  }

}
