package com.koval.jresolver.common.api.bean.issue;


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
}
