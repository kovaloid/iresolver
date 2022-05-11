package com.koval.resolver.common.api.configuration;

import com.koval.resolver.common.api.configuration.component.AdministrationConfiguration;
import com.koval.resolver.common.api.configuration.component.ConnectorsConfiguration;
import com.koval.resolver.common.api.configuration.component.ProcessorsConfiguration;
import com.koval.resolver.common.api.configuration.component.ReportersConfiguration;
import com.koval.resolver.common.api.configuration.component.VectorizerConfiguration;


public class Configuration {

  private AdministrationConfiguration administration;
  private ConnectorsConfiguration connectors;
  private VectorizerConfiguration vectorizer;
  private ProcessorsConfiguration processors;
  private ReportersConfiguration reporters;

  public AdministrationConfiguration getAdministration() {
    return administration;
  }

  public void setAdministration(final AdministrationConfiguration administration) {
    this.administration = administration;
  }

  public ConnectorsConfiguration getConnectors() {
    return connectors;
  }

  public void setConnectors(final ConnectorsConfiguration connectors) {
    this.connectors = connectors;
  }

  public VectorizerConfiguration getVectorizer() {
    return vectorizer;
  }

  public void setVectorizer(final VectorizerConfiguration vectorizer) {
    this.vectorizer = vectorizer;
  }

  public ProcessorsConfiguration getProcessors() {
    return processors;
  }

  public void setProcessors(final ProcessorsConfiguration processors) {
    this.processors = processors;
  }

  public ReportersConfiguration getReporters() {
    return reporters;
  }

  public void setReporters(final ReportersConfiguration reporters) {
    this.reporters = reporters;
  }

  @Override
  public String toString() {
    return "Configuration{"
        + "administration=" + administration
        + ", connectors=" + connectors
        + ", vectorizer=" + vectorizer
        + ", processors=" + processors
        + ", reporters=" + reporters
        + '}';
  }
}
