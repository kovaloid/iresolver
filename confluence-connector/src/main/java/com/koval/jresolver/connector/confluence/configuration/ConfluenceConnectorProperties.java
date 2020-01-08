package com.koval.jresolver.connector.confluence.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.koval.jresolver.common.api.exception.ConfigurationException;


public class ConfluenceConnectorProperties {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceConnectorProperties.class);
  private static final String CONNECTOR_PROPERTIES_FILE = "confluence-connector.properties";

  private String confluenceBaseUrl;
  private boolean anonymous = true;
  private String username = "";
  private String password = "";
  private List<String> spaceKeys;
  private int limitPerRequest = 500;
  private String workFolder = "";

  public ConfluenceConnectorProperties() {
    loadProperties();
  }

  private void loadProperties() {
    Properties properties = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONNECTOR_PROPERTIES_FILE)) {
      if (input == null) {
        throw new IOException("Could not find the " + CONNECTOR_PROPERTIES_FILE + " file at the classpath");
      }
      properties.load(input);
      confluenceBaseUrl = properties.getProperty("confluenceBaseUrl");
      anonymous = Boolean.parseBoolean(properties.getProperty("anonymous"));
      username = properties.getProperty("username");
      password = properties.getProperty("password");
      spaceKeys = Arrays.asList(properties.getProperty("spaceKeys").split(","));
      limitPerRequest = Integer.parseInt(properties.getProperty("limitPerRequest"));
      workFolder = properties.getProperty("workFolder");
    } catch (IOException e) {
      throw new ConfigurationException("Could not load the confluence connector properties.", e);
    }
    LOGGER.debug("Confluence connector configuration was loaded successfully.");
  }

  public String getConfluenceBaseUrl() {
    return confluenceBaseUrl;
  }

  public void setConfluenceBaseUrl(String confluenceBaseUrl) {
    this.confluenceBaseUrl = confluenceBaseUrl;
  }

  public boolean isAnonymous() {
    return anonymous;
  }

  public void setAnonymous(boolean anonymous) {
    this.anonymous = anonymous;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getLimitPerRequest() {
    return limitPerRequest;
  }

  public void setLimitPerRequest(int limitPerRequest) {
    this.limitPerRequest = limitPerRequest;
  }

  public List<String> getSpaceKeys() {
    return spaceKeys;
  }

  public void setSpaceKeys(List<String> spaceKeys) {
    this.spaceKeys = spaceKeys;
  }

  public String getWorkFolder() {
    return workFolder;
  }

  public void setWorkFolder(String workFolder) {
    this.workFolder = workFolder;
  }

  @Override
  public String toString() {
    return "ConfluenceConnectorProperties{"
        + "confluenceBaseUrl='" + confluenceBaseUrl + '\''
        + ", anonymous=" + anonymous
        + ", username='" + username + '\''
        + ", password='" + password + '\''
        + ", spaceKeys='" + spaceKeys + '\''
        + ", limitPerRequest=" + limitPerRequest
        + ", workFolder='" + workFolder + '\''
        + '}';
  }
}
