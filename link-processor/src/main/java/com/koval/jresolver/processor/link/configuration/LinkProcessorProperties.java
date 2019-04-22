package com.koval.jresolver.processor.link.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.exception.ConfigurationException;


public class LinkProcessorProperties {

  private static final Logger LOGGER = LoggerFactory.getLogger(LinkProcessorProperties.class);
  private static final String PROCESSOR_PROPERTIES_FILE = "link-processor.properties";

  private String targetLink;
  private String issueField;

  public LinkProcessorProperties() {
    loadProperties();
  }

  private void loadProperties() {
    Properties properties = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROCESSOR_PROPERTIES_FILE)) {
      if (input == null) {
        throw new IOException("Could not find the " + PROCESSOR_PROPERTIES_FILE + " file at the classpath");
      }
      properties.load(input);
      targetLink = properties.getProperty("targetLink");
      issueField = properties.getProperty("issueField");
    } catch (IOException e) {
      throw new ConfigurationException("Could not load the processor properties.", e);
    }
    LOGGER.debug("Link processor configuration was loaded successfully.");
  }


  public String getTargetLink() {
    return targetLink;
  }

  public void setTargetLink(String targetLink) {
    this.targetLink = targetLink;
  }

  public String getIssueField() {
    return issueField;
  }

  public void setIssueField(String issueField) {
    this.issueField = issueField;
  }

  @Override
  public String toString() {
    return "LinkProcessorProperties{"
        + "targetLink='" + targetLink + '\''
        + ", issueField='" + issueField + '\''
        + '}';
  }
}
