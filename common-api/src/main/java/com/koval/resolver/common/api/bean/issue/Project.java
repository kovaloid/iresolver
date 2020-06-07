package com.koval.resolver.common.api.bean.issue;


import java.util.Objects;

public class Project {

  private String key;
  private String name;

  public Project() {
  }

  public Project(final String key, final String name) {
    this.key = key;
    this.name = name;
  }

  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Project project = (Project)o;
    return Objects.equals(key, project.key)
        && Objects.equals(name, project.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, name);
  }
}
