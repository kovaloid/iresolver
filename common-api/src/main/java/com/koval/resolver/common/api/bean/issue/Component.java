package com.koval.resolver.common.api.bean.issue;


import java.util.Objects;

public class Component {

  private String name;
  private String description;

  public Component() {
  }

  public Component(final String name, final String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Component component = (Component)o;
    return Objects.equals(name, component.name)
        && Objects.equals(description, component.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description);
  }
}
