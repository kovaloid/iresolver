package com.koval.resolver.connector.bugzilla.configuration;


public class BugzillaQuery {

  private String product;
  private String status;
  private String resolution;
  private String assignee;
  private String reporter;
  private String version;
  private String priority;

  public String getProduct() {
    return product;
  }

  public void setProduct(final String product) {
    this.product = product;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(final String status) {
    this.status = status;
  }

  public String getResolution() {
    return resolution;
  }

  public void setResolution(final String resolution) {
    this.resolution = resolution;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(final String assignee) {
    this.assignee = assignee;
  }

  public String getReporter() {
    return reporter;
  }

  public void setReporter(final String reporter) {
    this.reporter = reporter;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(final String priority) {
    this.priority = priority;
  }

  @Override
  public String toString() {
    return "BugzillaQuery{"
        + "product='" + product + '\''
        + ", status='" + status + '\''
        + ", resolution='" + resolution + '\''
        + ", assignee='" + assignee + '\''
        + ", reporter='" + reporter + '\''
        + ", version='" + version + '\''
        + ", priority='" + priority + '\''
        + '}';
  }
}
