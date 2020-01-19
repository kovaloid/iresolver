package com.koval.jresolver.common.api.configuration;

import java.util.List;


public class AdministrationConfiguration {

  private String connector;
  private List<String> processors;
  private List<String> reporters;
  private boolean parallelExecution;

  public String getConnector() {
    return connector;
  }

  public void setConnector(String connector) {
    this.connector = connector;
  }

  public List<String> getProcessors() {
    return processors;
  }

  public void setProcessors(List<String> processors) {
    this.processors = processors;
  }

  public List<String> getReporters() {
    return reporters;
  }

  public void setReporters(List<String> reporters) {
    this.reporters = reporters;
  }

  public boolean isParallelExecution() {
    return parallelExecution;
  }

  public void setParallelExecution(boolean parallelExecution) {
    this.parallelExecution = parallelExecution;
  }

  @Override
  public String toString() {
    return "AdministrationConfiguration{"
        + "connector='" + connector + '\''
        + ", processors=" + processors
        + ", reporters=" + reporters
        + ", parallelExecution=" + parallelExecution
        + '}';
  }
}
