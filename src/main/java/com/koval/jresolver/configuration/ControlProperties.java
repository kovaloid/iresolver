package com.koval.jresolver.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.exception.ConfigurationException;


public class ControlProperties {

  private static final Logger LOGGER = LoggerFactory.getLogger(ControlProperties.class);
  private static final String CONTROL_PROPERTIES_FILE = "control.properties";

  private String connector;
  private List<String> processors;
  private List<String> reporters;
  private boolean parallel;

  public ControlProperties() {
    loadProperties();
  }

  private void loadProperties() {
    Properties properties = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONTROL_PROPERTIES_FILE)) {
      if (input == null) {
        throw new IOException("Could not find the " + CONTROL_PROPERTIES_FILE + " file at the classpath");
      }
      properties.load(input);
      connector = properties.getProperty("connector");
      processors = Arrays.asList(properties.getProperty("processors").split(","));
      reporters = Arrays.asList(properties.getProperty("reporters").split(","));
    } catch (IOException e) {
      throw new ConfigurationException("Could not load the control properties.", e);
    }
    LOGGER.debug("Control configuration was loaded successfully.");
  }

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

  public boolean isParallel() {
    return parallel;
  }

  public void setParallel(boolean parallel) {
    this.parallel = parallel;
  }

  @Override
  public String toString() {
    return "ControlProperties{"
        + "connector='" + connector + '\''
        + ", processors=" + processors
        + ", reporters=" + reporters
        + ", parallel=" + parallel
        + '}';
  }
}
