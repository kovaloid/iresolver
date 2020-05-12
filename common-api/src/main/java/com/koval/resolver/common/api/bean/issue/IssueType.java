package com.koval.resolver.common.api.bean.issue;

import java.util.Objects;

public class IssueType {

  private String name;
  private boolean isSubTask;

  public IssueType() {
  }

  public IssueType(final String name, final boolean isSubTask) {
    this.name = name;
    this.isSubTask = isSubTask;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public boolean isSubTask() {
    return isSubTask;
  }

  public void setSubTask(final boolean subTask) {
    isSubTask = subTask;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final IssueType issueType = (IssueType)o;
    return isSubTask == issueType.isSubTask
        && Objects.equals(name, issueType.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, isSubTask);
  }
}
