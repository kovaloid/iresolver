package com.koval.jresolver.common.api.bean.issue;


import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IssueField that = (IssueField)o;
    return Objects.equals(id, that.id)
        && Objects.equals(name, that.name)
        && Objects.equals(type, that.type)
        && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, type, value);
  }
}
