package com.koval.resolver.common.api.bean.issue;

import java.util.Objects;


public class IssueField {

  private String id;
  private String name;
  private String type;
  private Object value;

  public IssueField() {
  }

  public IssueField(final String name) {
    this.name = name;
  }

  public IssueField(final String id, final String name, final String type, final Object value) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.value = value;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(final Object value) {
    this.value = value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final IssueField that = (IssueField)o;
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
