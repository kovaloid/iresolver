package com.koval.resolver.common.api.configuration.bean;

import com.koval.resolver.common.api.constant.ConnectorType;

import java.util.List;


public class AdministrationConfiguration {

  private ConnectorType connectorType;
  private List<String> processors;
  private List<String> reporters;
  private boolean parallelExecution;

  public ConnectorType getConnectorType() {
    return connectorType;
  }

  public void setConnectorType(ConnectorType connectorType) {
    this.connectorType = connectorType;
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
        + "connector='" + connectorType + '\''
        + ", processors=" + processors
        + ", reporters=" + reporters
        + ", parallelExecution=" + parallelExecution
        + '}';
  }
}
