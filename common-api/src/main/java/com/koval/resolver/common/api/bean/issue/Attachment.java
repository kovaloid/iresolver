package com.koval.resolver.common.api.bean.issue;

import java.net.URI;
import java.util.Objects;

import org.joda.time.DateTime;


public class Attachment {

  private String fileName;
  private User author;
  private DateTime creationDate;
  private int size;
  private String mimeType;
  private URI contentUri;

  public Attachment() {
  }

  public Attachment(String fileName, User author, DateTime creationDate, int size, String mimeType, URI contentUri) {
    this.fileName = fileName;
    this.author = author;
    this.creationDate = creationDate;
    this.size = size;
    this.mimeType = mimeType;
    this.contentUri = contentUri;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public DateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(DateTime creationDate) {
    this.creationDate = creationDate;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public URI getContentUri() {
    return contentUri;
  }

  public void setContentUri(URI contentUri) {
    this.contentUri = contentUri;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Attachment that = (Attachment)o;
    return size == that.size
        && Objects.equals(fileName, that.fileName)
        && Objects.equals(author, that.author)
        && Objects.equals(creationDate, that.creationDate)
        && Objects.equals(mimeType, that.mimeType)
        && Objects.equals(contentUri, that.contentUri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileName, author, creationDate, size, mimeType, contentUri);
  }
}
