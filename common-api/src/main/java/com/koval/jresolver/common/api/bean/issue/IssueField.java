package com.koval.jresolver.common.api.bean.issue;


public class IssueField {

  private String id;
  private String name;
  private String type;
  private Object value;

  public IssueField() {
  }

  public IssueField(String id, String name, String type, Object value) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.value = value;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }
}
