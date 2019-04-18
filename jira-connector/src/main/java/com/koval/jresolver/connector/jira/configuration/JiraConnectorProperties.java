package com.koval.jresolver.connector.jira.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JiraConnectorProperties {

  private static final Logger LOGGER = LoggerFactory.getLogger(JiraConnectorProperties.class);
  private static final String CONNECTOR_PROPERTIES_FILE = "jira-connector.properties";

  private String url;
  private boolean anonymous = true;
  private String resolvedQuery;
  private String unresolvedQuery;
  private int batchSize = 10;
  private int batchDelay = 3000;
  private Set<String> resolvedIssueFields;
  private Set<String> unresolvedIssueFields;
  private String credentialsFolder = "../data/";

  public JiraConnectorProperties() {
    loadProperties();
  }

  private void loadProperties() {
    Properties properties = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONNECTOR_PROPERTIES_FILE)) {
      if (input == null) {
        throw new IOException("Could not find the " + CONNECTOR_PROPERTIES_FILE + " file at the classpath");
      }
      properties.load(input);
      url = properties.getProperty("url");
      anonymous = Boolean.parseBoolean(properties.getProperty("anonymous"));
      resolvedQuery = properties.getProperty("resolvedQuery");
      unresolvedQuery = properties.getProperty("unresolvedQuery");
      batchSize = Integer.parseInt(properties.getProperty("batchSize"));
      batchDelay = Integer.parseInt(properties.getProperty("batchDelay"));
      resolvedIssueFields = new HashSet<>(Arrays.asList(properties.getProperty("resolvedIssueFields").split(",")));
      unresolvedIssueFields = new HashSet<>(Arrays.asList(properties.getProperty("unresolvedIssueFields").split(",")));
      credentialsFolder = properties.getProperty("credentialsFolder");
    } catch (IOException e) {
      LOGGER.error("Could not load the connector properties. The default properties will be used.", e);
    }
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isAnonymous() {
    return anonymous;
  }

  public void setAnonymous(boolean anonymous) {
    this.anonymous = anonymous;
  }

  public String getResolvedQuery() {
    return resolvedQuery;
  }

  public void setResolvedQuery(String resolvedQuery) {
    this.resolvedQuery = resolvedQuery;
  }

  public String getUnresolvedQuery() {
    return unresolvedQuery;
  }

  public void setUnresolvedQuery(String unresolvedQuery) {
    this.unresolvedQuery = unresolvedQuery;
  }

  public int getBatchSize() {
    return batchSize;
  }

  public void setBatchSize(int batchSize) {
    this.batchSize = batchSize;
  }

  public int getBatchDelay() {
    return batchDelay;
  }

  public void setBatchDelay(int batchDelay) {
    this.batchDelay = batchDelay;
  }

  public Set<String> getResolvedIssueFields() {
    return resolvedIssueFields;
  }

  public void setResolvedIssueFields(Set<String> resolvedIssueFields) {
    this.resolvedIssueFields = resolvedIssueFields;
  }

  public Set<String> getUnresolvedIssueFields() {
    return unresolvedIssueFields;
  }

  public void setUnresolvedIssueFields(Set<String> unresolvedIssueFields) {
    this.unresolvedIssueFields = unresolvedIssueFields;
  }

  public String getCredentialsFolder() {
    return credentialsFolder;
  }

  public void setCredentialsFolder(String credentialsFolder) {
    this.credentialsFolder = credentialsFolder;
  }
}
