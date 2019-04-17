package com.koval.jresolver.connector.api;

import org.joda.time.DateTime;

import java.net.URI;


public class Attachment {

  private String fileName;
  private User author;
  private DateTime creationDate;
  private int size;
  private String mimeType;
  private URI contentUri;

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
}
