package com.koval.jresolver.common.api.configuration;


public class Configuration {

  private AdministrationConfiguration administration;
  private ConnectorsConfiguration connectors;
  private ParagraphVectorsConfiguration paragraphVectors;
  private ProcessorsConfiguration processors;
  private ReportersConfiguration reporters;

  public AdministrationConfiguration getAdministration() {
    return administration;
  }

  public void setAdministration(AdministrationConfiguration administration) {
    this.administration = administration;
  }

  public ConnectorsConfiguration getConnectors() {
    return connectors;
  }

  public void setConnectors(ConnectorsConfiguration connectors) {
    this.connectors = connectors;
  }

  public ParagraphVectorsConfiguration getParagraphVectors() {
    return paragraphVectors;
  }

  public void setParagraphVectors(ParagraphVectorsConfiguration paragraphVectors) {
    this.paragraphVectors = paragraphVectors;
  }

  public ProcessorsConfiguration getProcessors() {
    return processors;
  }

  public void setProcessors(ProcessorsConfiguration processors) {
    this.processors = processors;
  }

  public ReportersConfiguration getReporters() {
    return reporters;
  }

  public void setReporters(ReportersConfiguration reporters) {
    this.reporters = reporters;
  }

  @Override
  public String toString() {
    return "Configuration{"
        + "administration=" + administration
        + ", connectors=" + connectors
        + ", paragraphVectors=" + paragraphVectors
        + ", processors=" + processors
        + ", reporters=" + reporters
        + '}';
  }
}
