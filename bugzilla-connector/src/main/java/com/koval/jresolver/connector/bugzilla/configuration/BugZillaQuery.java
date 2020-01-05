package com.koval.jresolver.connector.bugzilla.configuration;


public class BugZillaQuery {

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

  public void setProduct(String product) {
    this.product = product;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getResolution() {
    return resolution;
  }

  public void setResolution(String resolution) {
    this.resolution = resolution;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getReporter() {
    return reporter;
  }

  public void setReporter(String reporter) {
    this.reporter = reporter;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  @Override
  public String toString() {
    return "BugZillaQuery{"
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
