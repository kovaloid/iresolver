package com.koval.jresolver.common.api.bean.issue;

import org.joda.time.DateTime;


public class Version {

  private String name;
  private String description;
  private boolean isArchived;
  private boolean isReleased;
  private DateTime releaseDate;

  public Version() {
  }

  public Version(String name, String description, boolean isArchived, boolean isReleased, DateTime releaseDate) {
    this.name = name;
    this.description = description;
    this.isArchived = isArchived;
    this.isReleased = isReleased;
    this.releaseDate = releaseDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isArchived() {
    return isArchived;
  }

  public void setArchived(boolean archived) {
    isArchived = archived;
  }

  public boolean isReleased() {
    return isReleased;
  }

  public void setReleased(boolean released) {
    isReleased = released;
  }

  public DateTime getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(DateTime releaseDate) {
    this.releaseDate = releaseDate;
  }
}
