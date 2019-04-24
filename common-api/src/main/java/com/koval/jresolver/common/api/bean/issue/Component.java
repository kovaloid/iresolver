package com.koval.jresolver.common.api.bean.issue;


import java.util.Objects;

public class Component {

  private String name;
  private String description;

  public Component() {
  }

  public Component(String name, String description) {
    this.name = name;
    this.description = description;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Component component = (Component)o;
    return Objects.equals(name, component.name)
        && Objects.equals(description, component.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description);
  }
}
