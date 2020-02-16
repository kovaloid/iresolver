package com.koval.resolver.common.api.bean.issue;


import java.util.Objects;

public class Project {

  private String key;
  private String name;

  public Project() {
  }

  public Project(String key, String name) {
    this.key = key;
    this.name = name;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Project project = (Project)o;
    return Objects.equals(key, project.key)
        && Objects.equals(name, project.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, name);
  }
}
