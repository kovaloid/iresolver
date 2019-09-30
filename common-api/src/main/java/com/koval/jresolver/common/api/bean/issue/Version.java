package com.koval.jresolver.common.api.bean.issue;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Version version = (Version)o;
    return isArchived == version.isArchived
        && isReleased == version.isReleased
        && Objects.equals(name, version.name)
        && Objects.equals(description, version.description)
        && Objects.equals(releaseDate, version.releaseDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, isArchived, isReleased, releaseDate);
  }
}
